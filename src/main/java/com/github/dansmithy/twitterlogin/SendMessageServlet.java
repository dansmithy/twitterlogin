package com.github.dansmithy.twitterlogin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class SendMessageServlet extends HttpServlet {

	private static final String PROTECTED_RESOURCE_URL = "https://api.twitter.com/1/direct_messages/new.json";
	
	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		OAuthService service = TwitterService.INSTANCE.getService();
		String message = (String)request.getParameter("message");
		
		Token accessToken = (Token)session.getAttribute("access_token");
		OAuthRequest oauthRequest = new OAuthRequest(Verb.POST,
				PROTECTED_RESOURCE_URL);
		oauthRequest.addBodyParameter("screen_name", "dansmithy");
		oauthRequest.addBodyParameter("text", message);
		service.signRequest(accessToken, oauthRequest);
		Response oauthResponse = oauthRequest.send();

		System.out.println(oauthResponse.getCode());
		System.out.println(IOUtils.toString(oauthResponse.getStream()));
//		String authorizationUrl = service.getAuthorizationUrl(requestToken);
		
	}

}
