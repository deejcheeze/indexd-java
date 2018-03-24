package cdis.indexd.auth;

import cdis.indexd.model.User;

public interface AuthTokenizer {
	
	public String create(User user);
	
	public User verify(String token);

}
