package cdis.indexd.api.impl;

import javax.ws.rs.core.Response;

import cdis.indexd.api.StatusService;

public class StatusServiceImpl implements StatusService {

	@Override
	public Response get() {
		return Response.ok().entity("Healthy").build();
	}

}
