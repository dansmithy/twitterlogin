package com.github.dansmithy.twitterlogin.service.impl;

import javax.inject.Named;

import org.apache.commons.lang.ArrayUtils;

import com.github.dansmithy.twitterlogin.model.TwitterUser;
import com.github.dansmithy.twitterlogin.service.RoleProvider;

@Named
public class BasicRoleProvider implements RoleProvider {

	private String[] adminUsernames = { "dan_bristol" };
	
	public BasicRoleProvider() {
		super();
	}

	@Override
	public String[] getRolesForUser(String username) {
		if (ArrayUtils.contains(adminUsernames, username)) {
			return new String[] { TwitterUser.ROLE_ADMIN };
		}
		return new String[] { TwitterUser.ROLE_USER };
	}

}
