package cdis.indexd.server;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;

import cdis.indexd.values.WsResponse;

@Provider
public class IndexException implements ExceptionMapper<BadRequestException> {
	
	@Inject
	private Logger logger;

	@Override
	public Response toResponse(BadRequestException exception) {
		logger.debug("Bad Request Exception: " + exception.getMessage());
		WsResponse<String> ws = new WsResponse<>(-1, "Bad Request Exception");
		ws.setEntity(exception.getMessage());
		return Response.status(Status.BAD_REQUEST).entity(ws).build();
	}

}
