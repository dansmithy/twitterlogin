package com.github.dansmithy.twitterlogin;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.oauth.OAuthService;

public enum TwitterService {

	INSTANCE;

	private final OAuthService oauthService = new ServiceBuilder()
			.provider(TwitterApi.class).apiKey("nYOW3tE8e96R7px104ez1w")
			.apiSecret(new SecretStore().getConsumerKey())
			.callback("http://localhost:8086/twitterCallback").build();
	
	
	public OAuthService getService() {
		return oauthService;
	}

}
