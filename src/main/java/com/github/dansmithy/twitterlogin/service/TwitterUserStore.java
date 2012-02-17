package com.github.dansmithy.twitterlogin.service;

import com.github.dansmithy.twitterlogin.rest.beans.TwitterUser;

public interface TwitterUserStore {

	void setCurrentUser(TwitterUser user);
	
	TwitterUser getCurrentUser();
}
