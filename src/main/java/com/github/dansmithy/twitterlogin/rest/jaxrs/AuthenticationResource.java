package com.github.dansmithy.twitterlogin.rest.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.github.dansmithy.twitterlogin.rest.beans.TwitterUser;

@Path("/ws/auth")
public interface AuthenticationResource {

	@GET
	@Path("/authToken")
	Response initiateAuth();

	@GET
	@Path("/authValidate")
	Response validateAuth(@QueryParam("oauth_token") String token, @QueryParam("oauth_verifier") String verifier, @QueryParam("denied") boolean denied);

	@GET
	@Path("/user")
	TwitterUser getUser();
}
