package com.github.dansmithy.twitterlogin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;

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
        String authorizationHeader = createAuthorizationHeader();
        method.addHeader("Authorization", authorizationHeader);
        System.out.println(authorizationHeader);
//        HttpResponse twitResponse = createClientWithProxy("172.16.42.42", 8080).execute(method);
//        System.out.println(twitResponse.getStatusLine().toString());
        System.out.println("done");
    }

    private String createAuthorizationHeader() throws UnsupportedEncodingException {

        String consumerKey = "nYOW3tE8e96R7px104ez1w";
        String signatureMethod = "HMAC-SHA1";
        String nonce = UUID.randomUUID().toString().replaceAll("-", "");
        String callback = "http://twitterlogin.herokuapp.com/twitterCallback";
        String oauthVersion = "1.0";
        String timestamp = Long.toString(new Date().getTime());

        OauthParams oauthParams = new OauthParams();
        oauthParams.add(new OauthParam("oauth_callback", callback));
        oauthParams.add(new OauthParam("oauth_consumer_key", consumerKey));
        oauthParams.add(new OauthParam("oauth_nonce", nonce));
        oauthParams.add(new OauthParam("oauth_signature_method", signatureMethod));
        oauthParams.add(new OauthParam("oauth_timestamp", timestamp));
        oauthParams.add(new OauthParam("oauth_version", oauthVersion));

        String signatureBaseString = oauthParams.createSignatureBaseString("POST", "https://api.twitter.com/oauth/request_token");
        String consumerSecret = "";
        String signingKey = consumerSecret + "&";
        String signature = Signature.calculateRFC2104HMAC(signatureBaseString, signingKey);
        oauthParams.add(new OauthParam("oauth_signature", signature));
        
        return oauthParams.createOathString();
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

    private static class OauthParams extends TreeSet<OauthParam> {

        @Override
        public boolean add(OauthParam param) {
            return super.add(param);
        }

        public String createSignatureParamString() {
        	System.out.println("**** create pama string ****");
            List<String> signatureValues = new ArrayList<String>();
//            List<OauthParam> params = new ArrayList<OauthParam>(this);
//            Collections.sort(params);
            for (OauthParam param : this) {
            	System.out.println(param);
                signatureValues.add(param.toSignatureString());
            }
            return StringUtils.join(signatureValues, "&");
        }

        public String createSignatureBaseString(String method, String url) {
            return String.format("%s&%s&%s", method.toUpperCase(), urlEncode(url), createSignatureParamString());
        }
        
        public String createOathString() {
        	System.out.println("**** create oauth string ****");
            List<String> signatureValues = new ArrayList<String>();
            for (OauthParam param : this) {
            	System.out.println(param);
                signatureValues.add(param.toOutputString());
            }
            return "OAuth " + StringUtils.join(signatureValues, ", ");        	
        }

    }

    private static class OauthParam implements Comparable<OauthParam> {

        private final String key;
        private final String value;

        public OauthParam(String key, String value) {
            this.key = urlEncode(key);
            this.value = urlEncode(value);
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public String toString() {
        	return toOutputString();
        }
        
        public String toOutputString() {
            return String.format("%s=\"%s\"", key, value);
        }

        public String toSignatureString() {
            return String.format("%s=%s", key, value);
        }

        @Override
        public int compareTo(OauthParam param) {
            return key.compareTo(param.key);
        }

    }

    private static String urlEncode(String text) {
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
