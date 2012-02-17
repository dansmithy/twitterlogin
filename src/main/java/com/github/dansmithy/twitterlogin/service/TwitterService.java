package com.github.dansmithy.twitterlogin.service;

import com.github.dansmithy.twitterlogin.model.TwitterUser;

public interface TwitterService {

	String getRedirectForAuthorization();
	
	void authenticateUser(String tokenKey, String oauthVerifier);
	
	TwitterUser getCurrentUser();
	
	void sendDirectMessage(String targetUser, String message);
}
