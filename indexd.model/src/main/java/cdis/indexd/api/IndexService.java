package cdis.indexd.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import cdis.indexd.annotations.IndexID;
import cdis.indexd.enums.Role;
import cdis.indexd.enums.Secured;
import cdis.indexd.values.Document;

@Path("index")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public interface IndexService {
	
	/**
	 * Retrieves an index entry by its IndexID
	 * @param indexId a valid index entry id
	 * @return index
	 */
	@GET @Path("{did}")
	public Document get(@IndexID @PathParam("did") String indexId);
	
	@GET
	public Document get(@QueryParam("limit") int limit, 
							@QueryParam("size") int size, 
							@QueryParam("start") int start, 
							@QueryParam("urls") String[] urls, 
							@QueryParam("file_name") String fileName, 
							@QueryParam("version") String version, 
							@QueryParam("metadata") String[] metadata);
	
	@POST
	public void add(Document index);
	
	@POST
	public void addRevision();
	
	@GET @Path("{did}/versions")
	public void listRevisions(@IndexID @PathParam("did") String indexId);
	
	@GET @Path("{did}/latest")
	public void getLatestRevisions(@IndexID @PathParam("did") String indexId);
	
	@PUT @Path("{did}") @Secured(Role.user)
	public void update(@IndexID @PathParam("did") String indexId);
	
	/**
	 * Deletes an index entry with the given id
	 * @param indexId document id
	 * @param revision
	 * @return 200 on success
	 */
	@DELETE @Path("{did}") @Secured(Role.user)
	public Response delete(@IndexID @PathParam("did") String indexId, @QueryParam("rev") String revision);

}
