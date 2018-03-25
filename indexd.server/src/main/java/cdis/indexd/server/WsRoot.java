package cdis.indexd.server;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

import cdis.indexd.api.impl.IndexServiceImpl;
import cdis.indexd.api.impl.UserServiceImpl;
import cdis.indexd.api.impl.VersionServiceImpl;

/**
 * Configures a JAX-RS endpoint. Delete this class, if you are not exposing
 * JAX-RS resources in your application.
 *
 * @author airhacks.com
 */
@ApplicationPath("")
public class WsRoot extends Application {
	
	private Set<Object> singletons = new HashSet<Object>();
	private HashSet<Class<?>> classes = new HashSet<Class<?>>();
	
	public WsRoot() {
		addResources();
		addCors();
	}
	
	public void addResources() {
		getClasses().add(UserServiceImpl.class);
		getClasses().add(IndexServiceImpl.class);
		getClasses().add(VersionServiceImpl.class);
		
		getClasses().add(IndexRequestFilter.class);
	}
	
	public void addSingleton(Class<?> clazz) {
		getSingletons().add(clazz);
	}
	
	public void addCors() {
		CorsFilter filter = new CorsFilter();
		filter.getAllowedOrigins().add("*");
		filter.getAllowedOrigins().add("10.0.0.109");
		filter.getAllowedOrigins().add("10.0.0.232");
		filter.setAllowedMethods("OPTIONS, GET, POST, DELETE, PUT, PATCH");
		filter.setAllowedHeaders("AUTHORIZATION, X-CLIENT-ID, X-CLIENT_SECRET, CONTENT-TYPE, DEVICE-TYPE, DEVICE-ID");
		getSingletons().add(filter);
	}
	
	@Override
	public Set<Class<?>> getClasses() {
		return this.classes;
	}
	
	@Override
	public Set<Object> getSingletons() {
		return this.singletons;
	}

}
