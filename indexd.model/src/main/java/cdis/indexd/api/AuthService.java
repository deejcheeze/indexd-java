package cdis.indexd.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cdis.indexd.enums.Role;
import cdis.indexd.enums.Secured;
import cdis.indexd.values.WsResponse;

@Path("auth")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public interface AuthService {
	
	/**
	 * Authenticates and returns a token used for 
	 * further authentication
	 * @return user token
	 */
	@POST @Path("login")
	public WsResponse<String> login();
	
	@Secured({Role.user})
	@POST @Path("logout")
	public WsResponse<?> logout();

}
