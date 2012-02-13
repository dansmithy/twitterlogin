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

public class VerifyUserServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		response.setContentType("application/json");
		String username = (String)session.getAttribute("username");
		if (username == null) {

			OAuthService service = new ServiceBuilder()
					.provider(TwitterApi.class)
					.apiKey("nYOW3tE8e96R7px104ez1w")
					.apiSecret(new SecretStore().getConsumerKey())
					.callback(
							"http://twitterlogin.herokuapp.com/twitterCallback")
					.build();

			Token requestToken = service.getRequestToken();
			session.setAttribute("request_token", requestToken);

			String authorizationUrl = service.getAuthorizationUrl(requestToken);
			response.getWriter().write(String.format("{ \"authorizationUrl\" : \"%s\" }", authorizationUrl));
			
		} else {
			response.getWriter().write(String.format("{ \"username\" : \"%s\" }", username));
		}
	}

}
