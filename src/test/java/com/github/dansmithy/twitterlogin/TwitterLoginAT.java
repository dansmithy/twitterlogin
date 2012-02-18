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
	
	@Test
	public void testAuth() {
		
		String token = "test_token";
		String tokenSecret = "test_token_secret";
		String tokenVerifier = "test_token_verifier";
		String authTokenReponseBody = String.format("oauth_token=%s&oauth_token_secret=%s&oauth_callback_confirmed=true", token, tokenSecret);
		clientDriver.addExpectation(onRequestTo("/oauth/request_token").withMethod(Method.POST), giveResponse(authTokenReponseBody).withStatus(HttpURLConnection.HTTP_OK));
		Response requestTokenResponse = get("http://localhost:8086/ws/auth/authToken");
		
		Assert.assertThat(requestTokenResponse.getStatusCode(), is(equalTo(HttpURLConnection.HTTP_MOVED_TEMP)));
		
		String location = requestTokenResponse.getHeader("location").getValue();
		Assert.assertThat(location, is(equalTo(String.format("http://localhost:%d/oauth/authenticate?oauth_token=%s", ATUtils.getRestDriverPort(), token))));
		
		Response validateUserResponse = get(String.format("http://localhost:8086/ws/auth/authValidate?oauth_token=%s&oauth_verifier=%s", token, tokenVerifier));
		Assert.assertThat(validateUserResponse.getStatusCode(), is(equalTo(HttpURLConnection.HTTP_MOVED_TEMP)));
		
	}
}
