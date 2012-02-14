package com.github.dansmithy.twitterlogin;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.exceptions.OAuthException;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.scribe.utils.OAuthEncoder;

public class TwitterCallback extends HttpServlet {

	private static final Pattern SCREEN_NAME_REGEX = Pattern.compile("screen_name=([^&]+)");
	
	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		response.setContentType("application/json");

		String denied = request.getParameter("denied");
		if (denied != null) {
			response.sendRedirect("/");
			return;
		}
		String oauthToken = request.getParameter("oauth_token");
		String oauthVerifier = request.getParameter("oauth_verifier");
		OAuthService service = TwitterService.INSTANCE.getService();

		Token requestToken = (Token)session.getAttribute("request_token");
		Token accessToken = service.getAccessToken(requestToken, new Verifier(oauthVerifier));
		session.setAttribute("access_token", accessToken);
		String screenName = extract(accessToken.getRawResponse(), SCREEN_NAME_REGEX);
		session.setAttribute("username", screenName);

		response.sendRedirect("/");
	}
	
	  private String extract(String response, Pattern p)
	  {
	    Matcher matcher = p.matcher(response);
	    if (matcher.find() && matcher.groupCount() >= 1)
	    {
	      return OAuthEncoder.decode(matcher.group(1));
	    }
	    else
	    {
	      throw new OAuthException("Response body is incorrect. Can't extract token and secret from this: '" + response + "'", null);
	    }
	  }	

}
