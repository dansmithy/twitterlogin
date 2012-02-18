package com.github.dansmithy.twitterlogin;

import static com.github.restdriver.serverdriver.RestServerDriver.*;
import static org.hamcrest.Matchers.*;

import java.net.HttpURLConnection;

import org.junit.Assert;
import org.junit.Test;

import com.github.restdriver.serverdriver.http.response.Response;

public class TwitterLoginAT {

	@Test
	public void testAuth() {
		
		Response response = get("http://localhost:8086/ws/auth/authToken");
		
		Assert.assertThat(response.getStatusCode(), is(equalTo(HttpURLConnection.HTTP_MOVED_TEMP)));
		
		String location = response.getHeader("location").getValue();
		
		System.out.println(location);
		
	}
}
