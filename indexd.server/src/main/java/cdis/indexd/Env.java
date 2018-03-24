package cdis.indexd;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.slf4j.Logger;


@Startup
@Singleton
public class Env {
	
	private Properties env;
	
	@Inject
	private transient Logger logger;
	
	@PostConstruct
	public void init() {
		
		env = new Properties();
		env.putAll(System.getenv());
		
		Properties props = read();
		for (Object key : props.keySet()) {
			env.put(key, props.get(key));
		}
		logger.info("Env initialized completely");
	}
	
	public String getEnv(String key) {
		return env.getProperty(key);
	}
	
	public String getEnv(String key, String defaultVal) {
		return env.getProperty(key, defaultVal);
	}
	
	private Properties read() {
		
		Properties env = new Properties();
		try (InputStream fis = new FileInputStream(".env")){
			logger.debug(".env found - loading environment variables");
			env.load(fis);
			
		} catch (Exception e) {
			logger.error("env file not found", e);
		}
		return env;
	}
	
	

}
