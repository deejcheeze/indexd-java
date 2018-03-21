package cdis.indexd.client;

import java.util.HashMap;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import cdis.indexd.api.IndexService;
import cdis.indexd.values.Document;

public class IndexClient {
	
	private String baseUrl;
	private String version;
	private String username;
	private String password;
	
	private Client client;

	public IndexClient(String baseUrl, String version, String username, String password) {
		this.baseUrl = baseUrl;
		this.version = version;
		this.username = username;
		this.password = password;
		
		this.client = ClientBuilder.newBuilder().build();
	}
	
	private IndexService getIndexService() {
		ResteasyWebTarget target = (ResteasyWebTarget) client.target(this.baseUrl + "/" + this.version);
		target.register(new IndexRequestFilter(new HashMap<>()));
		return target.proxy(IndexService.class);
	}
	
	public Document get(String did) {
		IndexService service = getIndexService();
		return service.get(did);
	}

}
