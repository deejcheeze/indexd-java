package cdis.indexd.auth;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import cdis.indexd.model.User;

@Singleton
public class AuthProvider {
	
	@Inject
	private JwtTokenizer jwtTokenizer;
	
	@Inject
	private BasicTokenizer basicTokenizer;
	
	public User authenticate(String authorization) {
		User user = null;
		if(authorization == null) {
			return null;
		}
		
		String[] authTokens = authorization.split(" ");
		if(authTokens.length != 2){
			return null;
		}
		
		String scheme = authTokens[0].toUpperCase(Locale.getDefault()); // Basic/Bearer
		String token = authTokens[1];
		
		
		if(scheme.equals("BASIC")) {
			user = basicTokenizer.verify(token);
		}else {
			// jwt login
			user = jwtTokenizer.verify(token);
		}
		return user;
	}

}
