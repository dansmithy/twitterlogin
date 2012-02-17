package com.github.dansmithy.twitterlogin.rest.beans;

import javax.inject.Named;

import com.github.dansmithy.twitterlogin.rest.jaxrs.SecuredResource;

@Named
public class SecuredBean implements SecuredResource {

	@Override
	public String text() {
		return "Gotta be a user, man";
	}

}
