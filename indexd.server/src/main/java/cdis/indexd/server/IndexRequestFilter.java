package cdis.indexd.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;

import cdis.indexd.auth.AuthTokenizer;
import cdis.indexd.enums.Role;
import cdis.indexd.enums.Secured;
import cdis.indexd.model.User;
import nw.orm.core.query.QueryParameter;
import nw.orm.jpa.JDao;
import nw.orm.jpa.JpaDaoFactory;

@Provider
public class IndexRequestFilter implements ContainerRequestFilter {
	
	@Inject
    private Logger logger;
	
	@Inject
    private AuthTokenizer tokenizer;
	
	@Inject
	private JpaDaoFactory factory;
	
	@Context
    private ResourceInfo resourceInfo;

	@Override
	public void filter(ContainerRequestContext ctx) throws IOException {
		
		Method mtd = resourceInfo.getResourceMethod();
		Class<?> rsc = resourceInfo.getResourceClass();
		String authorization = ctx.getHeaderString("Authorization");
		
		// check if annotation is on class level
		Secured ra = rsc.getAnnotation(Secured.class);
		if(ra == null || mtd.getAnnotation(Secured.class) != null) {
			ra = mtd.getAnnotation(Secured.class);
		}
		
		User authorizedUser = authenticate(authorization);
		if(authorizedUser != null && ra != null) {
			boolean allowed = false;
			Set<Role> rs = new HashSet<Role>(Arrays.asList(ra.value()));
			
			for (Role role : rs) {
				if(role == Role.none || role == authorizedUser.getRole()) {
					allowed = true;
					break;
				}
			}
			
			if(!allowed){
				throw new NotAuthorizedException("Bearer");
			}
		}
	}
	
	private User authenticate(String authText) throws UnsupportedEncodingException {
		if(authText == null) {
			return null;
		}
		
		String[] authTokens = authText.split(" ");
		if(authTokens.length != 2){
			return null;
		}
		
		String scheme = authTokens[0].toUpperCase(Locale.getDefault()); // Basic/Bearer
		String token = authTokens[1];
		
		
		if(scheme.equals("BASIC")) {
			doBasicLogin(token);
		}else {
			// jwt login
			User user = tokenizer.verify(token);
		}
		
		return null;
	}
	
	private void doBasicLogin(String authToken) throws UnsupportedEncodingException {
		String decodedAuth = new String(Base64.getDecoder().decode(authToken), "UTF-8");
		String[] tokens = decodedAuth.split(":"); // username:password
		String username = tokens[0];
		String password = tokens[1];
	}
	
	private void doJwtLogin() {
		
	}

}
