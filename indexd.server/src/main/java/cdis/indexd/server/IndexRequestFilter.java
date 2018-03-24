package cdis.indexd.server;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.Locale;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;

import cdis.indexd.enums.Secured;
import nw.orm.jpa.JpaDaoFactory;

@Provider
public class IndexRequestFilter implements ContainerRequestFilter {
	
	@Inject
    private Logger logger;
	
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
	}
	
	private void authenticate(String authText) {
		if(authText == null) {
			return null;
		}
		
		String[] authTokens = authText.split(" ");
		if(authTokens.length != 2){
			return null;
		}
		
		String scheme = authTokens[0].toUpperCase(Locale.getDefault()); // Basic
		String token = authTokens[1];
		
		String decodedAuth = new String(Base64.getDecoder().decode(token), "UTF-8");
		String[] tokens = decodedAuth.split(":"); // username|password
	}

}
