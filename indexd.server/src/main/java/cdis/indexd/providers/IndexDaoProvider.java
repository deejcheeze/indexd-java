package cdis.indexd.providers;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import org.slf4j.Logger;

import cdis.indexd.Env;
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
			em = Persistence.createEntityManagerFactory("nurlng", props);
		}
		logger.info("database initialization complete");
	}
	
	@Produces
	public JpaDaoFactory provideJpaDaoFactory() {
		return this.factory;
	}

}
