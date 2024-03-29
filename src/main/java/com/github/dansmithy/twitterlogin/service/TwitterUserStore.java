package com.github.dansmithy.twitterlogin.service;

import com.github.dansmithy.twitterlogin.model.OAuthToken;
import com.github.dansmithy.twitterlogin.model.TwitterUser;

public interface TwitterUserStore {

	void setCurrentUser(TwitterUser user);
	
	TwitterUser getCurrentUser();
	
	void rememberToken(OAuthToken token);
	
	OAuthToken getOAuthToken();
}
