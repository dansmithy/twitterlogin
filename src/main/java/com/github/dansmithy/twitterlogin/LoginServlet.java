package com.github.dansmithy.twitterlogin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class LoginServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpPost method = new HttpPost("https://api.twitter.com/oauth/request_token");
        
        TwitterRequest myAccount = new TwitterRequest()
        	.setConsumerKey("nYOW3tE8e96R7px104ez1w")
        	.setConsumerSecret(new SecretStore().getConsumerKey())
        	.setUrl("https://api.twitter.com/oauth/request_token")
        	.addParam(TwitterRequest.KEY_CALLBACK, "http://twitterlogin.herokuapp.com/twitterCallback");
        
        
        // see https://dev.twitter.com/docs/auth/authorizing-request

        
//        String authorizationHeader = createAuthorizationHeader(myAccount);
//        method.addHeader("Authorization", authorizationHeader);
//        System.out.println(authorizationHeader);
//        HttpResponse twitResponse = createClientWithProxy("172.16.42.42", 8080).execute(method);
//        System.out.println(twitResponse.getStatusLine().toString());
        System.out.println("done");
    }



    /**
     * Setup to create client for use within Nokia (ie. with a proxy set)
     */
    private HttpClient createClientWithProxy(String proxyHost, int port) {
        HttpClient client = new DefaultHttpClient();
//        HttpHost proxy = new HttpHost(proxyHost, port);
//        client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        return client;
    }

    public static void main(String[] args) throws ServletException, IOException {
        new LoginServlet().service(null, null);
    }

}
