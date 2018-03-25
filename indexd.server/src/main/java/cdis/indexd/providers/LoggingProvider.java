package cdis.indexd.providers;

import javax.ejb.Singleton;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class LoggingProvider {
	
	@Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
		
		String className = injectionPoint.getMember().getDeclaringClass().getName();
        return LoggerFactory.getLogger(className);
    }

}
