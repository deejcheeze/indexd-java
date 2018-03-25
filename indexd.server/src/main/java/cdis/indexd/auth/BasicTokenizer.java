package cdis.indexd.auth;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;

import cdis.indexd.model.User;
import nw.orm.core.query.QueryParameter;
import nw.orm.jpa.JDao;
import nw.orm.jpa.JpaDaoFactory;

@Stateless
public class BasicTokenizer {
	
	@Inject
	private Logger logger;
	
	private JDao<User> userDao;
	
	@Inject
	private JpaDaoFactory daoFactory;
	
	@PostConstruct
	public void init() {
		userDao = daoFactory.getDao(User.class);
	}

	public User verify(String token) {
		
		try {
			String decodedAuth = new String(Base64.getDecoder().decode(token), "UTF-8");
			String[] tokens = decodedAuth.split(":"); // username:password
			String username = tokens[0];
			String password = tokens[1];
			
			User user = userDao.find(QueryParameter.create("username", username));
			if(user != null && BCrypt.checkpw(password, user.getPassword())) {
				return user;
			}
			
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception ", e);
		}
		return null;
	}

}
