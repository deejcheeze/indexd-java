package cdis.indexd.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

public class IndexRequestFilter implements ClientRequestFilter {
	
	private Map<String, String> headers = new HashMap<>();
	
	public IndexRequestFilter(Map<String, String> keys) {
		if(keys != null)
			this.headers = keys;
	}
	
	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {

		requestContext.getHeaders().add("Content-Type", "application/json");
		
		requestContext.getHeaders().add("Accept", "application/json,text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		requestContext.getHeaders().add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:57.0) Gecko/20100101 Firefox/57.0");
		
		headers.forEach((k, v) -> {
			requestContext.getHeaders().add(k, v);
		});
	}

}
