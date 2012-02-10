package com.github.dansmithy.twitterlogin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

public class LoginServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpPost method = new HttpPost("https://api.twitter.com/oauth/request_token");
        method.addHeader("Authorization", createAuthorizationHeader());
        //        System.out.println(createAuthorizationHeader());
        //        HttpResponse twitResponse = createClientWithProxy("172.16.42.42", 8080).execute(method);
    }

    private String createAuthorizationHeader() throws UnsupportedEncodingException {

        String consumerKey = "nYOW3tE8e96R7px104ez1w";
        String signature = "";
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

        System.out.println(oauthParams.createSignatureBaseString("POST", "https://api.twitter.com/oauth/request_token"));

        //        oauthParams.add(new OauthParam("oauth_signature", signature));
        return String.format("OAuth oauth_callback=\"%s\", oauth_consumer_key=\"%s\", oauth_nonce=\"%s\", oauth_signature=\"%s\", oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"%d\", oauth_version=\"1.0\"", callback, consumerKey, nonce, signature, new Date().getTime());
    }

    /**
     * Setup to create client for use within Nokia (ie. with a proxy set)
     */
    private HttpClient createClientWithProxy(String proxyHost, int port) {
        HttpClient client = new DefaultHttpClient();
        HttpHost proxy = new HttpHost(proxyHost, port);
        client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
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
            List<String> signatureValues = new ArrayList<String>();
            for (OauthParam param : this) {
                signatureValues.add(param.toSignatureString());
            }
            return StringUtils.join(signatureValues, "&");
        }

        public String createSignatureBaseString(String method, String url) {

            return String.format("%s&%s&%s", method.toUpperCase(), urlEncode(url), createSignatureParamString());
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
