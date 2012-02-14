package com.github.dansmithy.twitterlogin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

public class StartAuthenticationServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		OAuthService service = TwitterService.INSTANCE.getService();

		Token requestToken = service.getRequestToken();
		session.setAttribute("request_token", requestToken);

		String authorizationUrl = service.getAuthorizationUrl(requestToken);
		response.sendRedirect(authorizationUrl);
	}

}
