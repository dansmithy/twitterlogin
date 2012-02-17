package com.github.dansmithy.twitterlogin.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Named;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.scribe.utils.OAuthEncoder;

import com.github.dansmithy.twitterlogin.model.TwitterUser;
import com.github.dansmithy.twitterlogin.service.RoleProvider;
import com.github.dansmithy.twitterlogin.service.SecretStore;
import com.github.dansmithy.twitterlogin.service.TwitterService;
import com.github.dansmithy.twitterlogin.service.TwitterUserStore;
import com.github.dansmithy.twitterlogin.service.exception.TwitterAuthRuntimeException;

@Named
public class ScribeTwitterService implements TwitterService {

	private static final String TWITTER_CONSUMER_KEY = "nYOW3tE8e96R7px104ez1w";
	private static final String TWITTER_CALLBACK_URL = "/ws/auth/authValidate";

	private static final Pattern SCREEN_NAME_REGEX = Pattern.compile("screen_name=([^&]+)");

	private final TwitterUserStore twitterUserStore;
	private final OAuthService oauthService;

	/**
	 * This needs improving. Either put a single token in the session, or expire these tokens.
	 */
	private Map<String, Token> tokens = new HashMap<String, Token>();
	private final RoleProvider roleProvider;

	@Inject
	public ScribeTwitterService(TwitterUserStore twitterUserStore, SecretStore secretStore, RoleProvider roleProvider) {
		super();
		this.twitterUserStore = twitterUserStore;
		this.roleProvider = roleProvider;
		this.oauthService = new ServiceBuilder().provider(TwitterApi.Authenticate.class).apiKey(TWITTER_CONSUMER_KEY)
				.apiSecret(secretStore.getConsumerKey()).callback(createCallback(secretStore.getBaseUrl(), TWITTER_CALLBACK_URL)).build();
	}

	@Override
	public String getRedirectForAuthorization() {
		Token requestToken = oauthService.getRequestToken();
		tokens.put(requestToken.getToken(), requestToken);
		return oauthService.getAuthorizationUrl(requestToken);
	}

	@Override
	public void authenticateUser(String tokenKey, String oauthVerifier) {
		if (!tokens.containsKey(tokenKey)) {
			throw new TwitterAuthRuntimeException(String.format("Attempt to validate authentication against an unknown token"));
		}
		Token requestToken = tokens.get(tokenKey);
		Token accessToken = oauthService.getAccessToken(requestToken, new Verifier(oauthVerifier));
		String screenName = extractUsingRegex(accessToken.getRawResponse(), SCREEN_NAME_REGEX);
		TwitterUser twitterUser = new TwitterUser(screenName, accessToken, roleProvider.getRolesForUser(screenName));
		twitterUserStore.setCurrentUser(twitterUser);
	}

	@Override
	public TwitterUser getCurrentUser() {
		return twitterUserStore.getCurrentUser();
	}

	@Override
	public void sendDirectMessage(String targetUser, String message) {
		// TODO Auto-generated method stub

	}

	private String extractUsingRegex(String response, Pattern p) {
		Matcher matcher = p.matcher(response);
		if (matcher.find() && matcher.groupCount() >= 1) {
			return OAuthEncoder.decode(matcher.group(1));
		} else {
			throw new TwitterAuthRuntimeException("Response body is incorrect. Can't extract details from response", null);
		}
	}
	
	private String createCallback(String hostname, String callbackUrl) {
		return hostname + callbackUrl;
	}
	
}
