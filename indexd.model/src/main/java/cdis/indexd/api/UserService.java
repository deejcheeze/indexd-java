package cdis.indexd.api;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import cdis.indexd.model.User;


@Path("acct")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public interface UserService {
	
	@POST @Path("add")
	public Response add(@FormParam("username") String username, @FormParam("password") String password);
	
	@POST @Path("enroll")
	public Response enroll(@Valid User user);
	
	@PUT @Path("verify")
	public Response verify(@Valid User user);

}
