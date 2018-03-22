package cdis.indexd.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import cdis.indexd.values.IndexParams;

@Path("urls")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public interface UrlService {
	
	@GET
	public IndexParams list(@QueryParam("limit") @DefaultValue("100") int limit, 
			@QueryParam("size") int size, 
			@QueryParam("start") @DefaultValue("0") int start, 
			@QueryParam("urls") String[] urls, 
			@QueryParam("hash") String[] hashes);

}
