package cdis.indexd.api.impl;

import javax.ws.rs.core.Response;

import cdis.indexd.api.VersionService;

public class VersionServiceImpl implements VersionService {

	@Override
	public Response get() {
		return Response.ok().entity("IndexD on Java 8").build();
	}

}
