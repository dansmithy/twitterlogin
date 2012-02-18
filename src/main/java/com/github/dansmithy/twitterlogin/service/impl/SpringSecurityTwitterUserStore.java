package com.github.dansmithy.twitterlogin.service.impl;

import javax.inject.Named;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.github.dansmithy.twitterlogin.model.OAuthToken;
import com.github.dansmithy.twitterlogin.model.TwitterUser;
import com.github.dansmithy.twitterlogin.service.TwitterUserStore;
import com.github.dansmithy.twitterlogin.service.exception.TwitterAuthRuntimeException;

@Named
public class SpringSecurityTwitterUserStore implements TwitterUserStore {

	@Override
	public void setCurrentUser(TwitterUser user) {
		getContext().setAuthentication(user);
	}

	@Override
	public TwitterUser getCurrentUser() {
		Object o = getContext().getAuthentication();
		if (o instanceof TwitterUser) {
			return (TwitterUser) o;
		}
		return TwitterUser.EMPTY_USER;
	}

	private SecurityContext getContext() {
		return SecurityContextHolder.getContext();
	}

	@Override
	public void rememberToken(OAuthToken token) {
		if (getContext().getAuthentication() == null) {
			throw new RuntimeException("No authentication");
		}
		setCurrentUser(new TwitterUser.PotentialTwitterUser(token));
	}

	@Override
	public OAuthToken getOAuthToken() {
		if (getCurrentUser().getAccessToken() == null) {
			throw new TwitterAuthRuntimeException("No token found!");
		}
		return getCurrentUser().getAccessToken();
	}

}
