package cdis.indexd.providers;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import org.hibernate.internal.SessionImpl;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;

import cdis.indexd.Env;
import cdis.indexd.enums.Role;
import cdis.indexd.model.User;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import nw.orm.core.query.QueryParameter;
import nw.orm.jpa.JDao;
import nw.orm.jpa.JpaDaoFactory;

@Startup
@Singleton
public class IndexDaoProvider {
	
	@Inject
	private Env env;
	
	@Inject
	private Logger logger;
	
	private JpaDaoFactory factory;
	
	@PersistenceUnit(unitName = "indexd")
	private EntityManagerFactory em;
	
	@PostConstruct
	public void init() {
		String dbType = env.getEnv("db.type", "default");
		logger.info("initializing database provider for: " + dbType);
		
		if(em == null) {
			Map<String, String> props = new HashMap<>();
			em = Persistence.createEntityManagerFactory("indexd", props);
		}
		factory = new JpaDaoFactory(em, true);
		
		migrate();
		initAdmin();
		logger.info("database initialization complete");
	}
	
	@Produces
	public JpaDaoFactory provideJpaDaoFactory() {
		return this.factory;
	}
	
	protected void migrate() {
		logger.debug("Starting Database Migration");
		Map<String, String> props = new HashMap<>();
		props.put("javax.persistence.jdbc.url", env.getEnv("db.url", "jdbc:postgresql://localhost/indexd"));
		props.put("javax.persistence.jdbc.user", env.getEnv("db.user", "postgres"));
		props.put("javax.persistence.jdbc.password", env.getEnv("db.pass", "postgres"));
		props.put("javax.persistence.jdbc.driver", env.getEnv("db.driver", "org.postgresql.Driver"));
		props.put("hibernate.dialect", env.getEnv("db.dialect", "org.hibernate.dialect.PostgreSQL9Dialect"));
		EntityManagerFactory em = Persistence.createEntityManagerFactory("indexd-local", props);
		
		EntityManager mgr = em.createEntityManager();
		Connection connection = mgr.unwrap(SessionImpl.class).connection();
		Database database = null;
		Liquibase liquibase = null;
		Contexts ctx = new Contexts("dev");

		try {
			database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
			liquibase = new Liquibase("liquibase/changelog-master.xml", new ClassLoaderResourceAccessor(), database);
			liquibase.update(ctx);
			
			em.close();
			logger.debug("Migration completed successfully");
		} catch (LiquibaseException e) {
			logger.error("Exception ", e);
		}
	}
	
	protected void initAdmin() {
		String adminUsername = env.getEnv("admin.username", "admin");
		String adminPwd = env.getEnv("admin.password", "admin");
		JDao<User> userDao = this.factory.getDao(User.class);
		
		User user = userDao.find(QueryParameter.create("username", adminUsername));
		if(user == null) {
			user = new User();
			user.setRole(Role.admin);
			user.setPassword(BCrypt.hashpw(adminPwd, BCrypt.gensalt()));
			user.setUsername(adminUsername);
			user.setVerified(true);
			
			userDao.save(user);
		}else if(!BCrypt.checkpw(adminPwd, user.getPassword())) {
			user.setPassword(BCrypt.hashpw(adminPwd, BCrypt.gensalt()));
			userDao.update(user);
		}
	}

}
