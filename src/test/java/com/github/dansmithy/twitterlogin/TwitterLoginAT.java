package com.github.dansmithy.twitterlogin;

import static com.github.restdriver.clientdriver.RestClientDriver.*;
import static com.github.restdriver.serverdriver.RestServerDriver.*;
import static org.hamcrest.Matchers.*;

import java.net.HttpURLConnection;

import org.junit.Assert;
import org.junit.Test;

import com.github.restdriver.clientdriver.ClientDriverRule;
import com.github.restdriver.clientdriver.ClientDriverRequest.Method;
import com.github.restdriver.serverdriver.http.response.Response;

public class TwitterLoginAT {

	public ClientDriverRule clientDriver = new ClientDriverRule(ATUtils.getRestDriverPort());
	
	private static final String BASE_URL = "http://localhost:8086";
	@Test
	public void testAuth() {
		
		String requestToken = "request_token";
		String requestTokenSecret = "request_token_secret";
		String accessToken = "access_token";
		String accessTokenSecret = "access_token_secret";
		String accessTokenVerifier = "access_token_verifier";
		String userId = "user_id";
		String username = "username";
		
		String requestTokenReponseBody = String.format("oauth_token=%s&oauth_token_secret=%s&oauth_callback_confirmed=true", requestToken, requestTokenSecret);
		clientDriver.addExpectation(onRequestTo("/oauth/request_token").withMethod(Method.POST), giveResponse(requestTokenReponseBody).withStatus(HttpURLConnection.HTTP_OK));
		Response requestTokenResponse = get(BASE_URL + "/ws/auth/authToken");
		
		Assert.assertThat(requestTokenResponse.getStatusCode(), is(equalTo(HttpURLConnection.HTTP_MOVED_TEMP)));
		
		String twitterLoginUrl = requestTokenResponse.getHeader("location").getValue();
		Assert.assertThat(twitterLoginUrl, is(equalTo(String.format("http://localhost:%d/oauth/authenticate?oauth_token=%s", ATUtils.getRestDriverPort(), requestToken))));
		
		String accessTokenReponseBody = String.format("oauth_token=%s&oauth_token_secret=%s&user_id=%s&screen_name=%s", accessToken, accessTokenSecret, userId, username);
		clientDriver.addExpectation(onRequestTo("/oauth/access_token").withMethod(Method.POST), giveResponse(accessTokenReponseBody).withStatus(HttpURLConnection.HTTP_OK));
		Response validateUserResponse = get(String.format(BASE_URL + "/ws/auth/authValidate?oauth_token=%s&oauth_verifier=%s", requestToken, accessTokenVerifier));
		Assert.assertThat(validateUserResponse.getStatusCode(), is(equalTo(HttpURLConnection.HTTP_MOVED_TEMP)));
		
	}
}
