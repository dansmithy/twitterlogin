package com.github.dansmithy.twitterlogin.service.impl;

import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.github.dansmithy.twitterlogin.model.TwitterUser;
import com.github.dansmithy.twitterlogin.service.TwitterUserStore;

@Named
public class SpringSecurityTwitterUserStore implements TwitterUserStore {

	@Override
	public void setCurrentUser(TwitterUser user) {
		getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
	}

	@Override
	public TwitterUser getCurrentUser() {
		Object o = getContext().getAuthentication().getPrincipal();
		if (o instanceof TwitterUser) {
			return (TwitterUser) o;
		}
		return TwitterUser.EMPTY_USER;
	}

	private SecurityContext getContext() {
		return SecurityContextHolder.getContext();
	}

}
