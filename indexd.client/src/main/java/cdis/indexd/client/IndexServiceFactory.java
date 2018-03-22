package cdis.indexd.client;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class IndexServiceFactory {
	
	private String baseUrl;
	private String authorization;
	private Client requestClient;

	public IndexServiceFactory(String baseUrl, String auth) {
		this.baseUrl = baseUrl;
		this.authorization = auth;
		this.requestClient = ClientBuilder.newBuilder().build();
	}
	
	public <T> T getService(Class<T> serviceClass, Map<String, String> headers) {
		ResteasyWebTarget target = (ResteasyWebTarget) requestClient.target(this.baseUrl);
		
		if(headers == null) {
			headers = new HashMap<>();
		}
		headers.put("Authorization", this.authorization);
		target.register(new IndexRequestFilter(headers));
		return target.proxy(serviceClass);
	}

}
