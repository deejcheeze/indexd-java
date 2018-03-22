package cdis.indexd.client;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import cdis.indexd.api.IndexService;
import cdis.indexd.values.Document;
import cdis.indexd.values.IndexParams;

public class IndexClient {
	
	private IndexServiceFactory serviceFactory;
	
	public IndexClient(String baseUrl, String version, String username, String password) {
		
		String auth = username + ":" + password;
		auth = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
		this.serviceFactory = new IndexServiceFactory(baseUrl + version, auth);
	}
	
	public Document get(String did) {
		IndexService service = serviceFactory.getService(IndexService.class, new HashMap<>());
		return service.get(did);
	}
	
	public IndexParams getWithParams(IndexParams params) {
		IndexService service = serviceFactory.getService(IndexService.class, new HashMap<>());
		IndexParams indexIds = service.get(params.getLimit(), params.getSize(), params.getStart(), params.getUrls(), 
				params.getHashes(), params.getFileName(), params.getVersion(), params.getMetadata());
		
		return indexIds;
	}
	
	public void listWithParams(IndexParams params) {
		IndexService service = serviceFactory.getService(IndexService.class, new HashMap<>());
	}
	
	public Document create(Document doc) {
		IndexService service = serviceFactory.getService(IndexService.class, new HashMap<>());
		doc = service.add(doc);
		return doc;
	}
	
	public Document create(String did, String fileName, int size, String[] urls, Map<String, String> hashes, Map<String, Object> metadata) {
		Document doc = new Document(did, urls, fileName, size, hashes, metadata);
		return this.create(doc);
	}
	
	public static void main(String[] args) {
		IndexClient client = new IndexClient("http://localhost:8080", "/", "test", "test");
		Map<String, String> hashes = new HashMap<>();
		hashes.put("md5", "ab167e49d25b488939b1ede42752458c");
//		Document doc = client.create(null, "aaa.txt", 10, new String[] {"google.com"}, hashes, new HashMap<>());
		IndexParams params = new IndexParams();
		params.setHashes(new String[] {"md5:ab167e49d25b488939b1ede42752458c"});
		params.setMetadata(new String[] {"age:3"});
		params.setUrls(new String[0]);
		IndexParams document = client.getWithParams(params);

		System.out.println(document);
	}

}
