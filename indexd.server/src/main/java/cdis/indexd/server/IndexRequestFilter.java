package cdis.indexd.server;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;

import cdis.indexd.auth.AuthProvider;
import cdis.indexd.enums.Role;
import cdis.indexd.enums.Secured;
import cdis.indexd.model.User;

@Provider
public class IndexRequestFilter implements ContainerRequestFilter {
	
	@Inject
    private Logger logger;
	
	@Inject
    private AuthProvider authProvider;
	
	@Context
    private ResourceInfo resourceInfo;
	
	@Inject
	private HttpServletRequest request;

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
		
		logger.info("Logging Request from: " + authorization);
		User authorizedUser = authProvider.authenticate(authorization);
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
			request.setAttribute("loggedin.user", authorizedUser);
		}
	}

}
