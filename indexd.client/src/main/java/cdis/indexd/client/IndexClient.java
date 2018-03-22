package cdis.indexd.client;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import cdis.indexd.api.IndexService;
import cdis.indexd.values.Document;

public class IndexClient {
	
	private String baseUrl;
	private String version;
	
	private Client client;
	private String auth;

	public IndexClient(String baseUrl, String version, String username, String password) {
		this.baseUrl = baseUrl;
		this.version = version;
		
		auth = username + ":" + password;
		auth = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
		this.client = ClientBuilder.newBuilder().build();
	}
	
	private IndexService getIndexService() {
		ResteasyWebTarget target = (ResteasyWebTarget) client.target(this.baseUrl);
		Map<String, String> attribs = new HashMap<>();
		attribs.put("Authorization", this.auth);
		target.register(new IndexRequestFilter(attribs));
		return target.proxy(IndexService.class);
	}
	
	public Document get(String did) {
		IndexService service = getIndexService();
		return service.get(did);
	}
	
	public Document create(String did, String fileName, int size, String[] urls, Map<String, String> hashes, Map<String, Object> metadata) {
		Document doc = new Document(did, urls, fileName, size, hashes, metadata);
		IndexService service = getIndexService();
		Document add = service.add(doc);
		return add;
	}
	
	public static void main(String[] args) {
		IndexClient client = new IndexClient("http://localhost:8080/", "v0", "test", "test");
		Map<String, String> hashes = new HashMap<>();
		hashes.put("md5", "ab167e49d25b488939b1ede42752458c");
		Document doc = client.create(null, "aaa.txt", 10, new String[] {"google.com"}, hashes, new HashMap<>());
		Document document = client.get(doc.getDid());

		System.out.println(document);
	}

}
