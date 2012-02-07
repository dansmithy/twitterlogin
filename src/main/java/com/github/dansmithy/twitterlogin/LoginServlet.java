package com.github.dansmithy.twitterlogin;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class LoginServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpPost method = new HttpPost("https://api.twitter.com/oauth/request_token");
        method.addHeader("Authorization", createAuthorizationHeader());
        HttpResponse twitResponse = new DefaultHttpClient().execute(method);
    }

    private String createAuthorizationHeader() {
        String consumerKey = "nYOW3tE8e96R7px104ez1w";
        String nonce = "";
        String signature = "";
        return String.format("OAuth oauth_callback=\"%s\", oauth_consumer_key=\"%s\", oauth_nonce=\"%s\", oauth_signature=\"%s\", oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"%d\", oauth_version=\"1.0\"", consumerKey, nonce, signature, new Date().getTime());
    }

}
