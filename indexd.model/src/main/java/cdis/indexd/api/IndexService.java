package cdis.indexd.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cdis.indexd.annotations.IndexID;
import cdis.indexd.values.Document;

@Path("get")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public interface IndexService {
	
	/**
	 * Retrieves an index entry by its IndexID
	 * @param indexId a valid index entry id
	 */
	@GET @Path("index")
	public Document get(@IndexID String indexId);

}
