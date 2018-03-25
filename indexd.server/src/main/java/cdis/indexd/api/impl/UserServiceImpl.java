package cdis.indexd.api.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;

import cdis.indexd.api.UserService;
import cdis.indexd.model.User;
import nw.orm.core.query.QueryParameter;
import nw.orm.jpa.JDao;
import nw.orm.jpa.JpaDaoFactory;

public class UserServiceImpl implements UserService {
	
	@Inject
	private Logger logger;
	
	private JDao<User> userDao;
	
	@Inject
	private JpaDaoFactory daoFactory;
	
	@PostConstruct
	public void init() {
		userDao = daoFactory.getDao(User.class);
	}

	@Override
	public Response add(String username, String password) {
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		logger.info("Account creation request for: " + username);
		return this.enroll(user);
	}

	@Override
	public Response enroll(User user) {
		User exUser = userDao.find(QueryParameter.create("username", user.getUsername()));
		
		if(exUser != null) {
			throw new WebApplicationException("Username is not available", Status.BAD_REQUEST);
		}
		String pwHash = BCrypt.hashpw(user.getUsername(), BCrypt.gensalt());
		user.setPassword(pwHash);
		user.setVerified(false);
		user.setBlacklisted(false);
		userDao.save(exUser);
		return Response.ok().entity("Successful").build();
	}

	@Override
	public Response verify(User user) {
		throw new WebApplicationException(Status.FORBIDDEN);
	}

}
