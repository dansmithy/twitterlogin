package com.github.dansmithy.twitterlogin;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TwitterRequest {

	public static final String KEY_CALLBACK = "oauth_callback";
	public static final String KEY_TOKEN = "oauth_token";
	
	private static final String DEFAULT_ENCODING_METHOD = "HMAC-SHA1";
	private static final String DEFAULT_VERSION = "1.0";
	private static final String DEFAULT_HTTP_METHOD = "POST";
	
	private String consumerKey;
	private String consumerSecret;
	private String signatureMethod = DEFAULT_ENCODING_METHOD;
	private String nonce;
	private String timestamp;
	private String version = DEFAULT_VERSION;
	
//	private String callback;
	
	private String url;
	private String httpMethod = DEFAULT_HTTP_METHOD;
	private String tokenSecret = "";
	
	private Map<String, String> otherParams = new HashMap<String, String>();
	
	public TwitterRequest() {
		super();
		nonce = UUID.randomUUID().toString().replaceAll("-", "");
        long time = new Date().getTime() / 1000 - 5;
        timestamp = Long.toString(time);

		
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public TwitterRequest setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
		return this;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public TwitterRequest setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
		return this;
	}

	public String getSignatureMethod() {
		return signatureMethod;
	}

	public TwitterRequest setSignatureMethod(String signatureMethod) {
		this.signatureMethod = signatureMethod;
		return this;
	}

	public String getNonce() {
		return nonce;
	}

	public TwitterRequest setNonce(String nonce) {
		this.nonce = nonce;
		return this;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public TwitterRequest setTimestamp(String timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	public String getVersion() {
		return version;
	}

	public TwitterRequest setVersion(String version) {
		this.version = version;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public TwitterRequest setUrl(String url) {
		this.url = url;
		return this;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public TwitterRequest setHttpMethod(String method) {
		this.httpMethod = method;
		return this;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public TwitterRequest setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
		return this;
	}
	
	public TwitterRequest addParam(String key, String value) {
		otherParams.put(key, value);
		return this;
	}

	public Map<String, String> getOtherParams() {
		return Collections.unmodifiableMap(otherParams);
	}
	
	
	
	
	
}
