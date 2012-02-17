package com.github.dansmithy.twitterlogin.rest.jaxrs;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.github.dansmithy.twitterlogin.model.TwitterUser;

@Path("/ws/secure")
@RolesAllowed({ TwitterUser.ROLE_USER })
public interface SecuredResource {

	@GET
	@Path("/text")
	String text();

}
