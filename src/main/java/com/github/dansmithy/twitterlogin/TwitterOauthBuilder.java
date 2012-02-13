package com.github.dansmithy.twitterlogin;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

public class TwitterOauthBuilder {

	private final TwitterRequest request;

	public TwitterOauthBuilder(TwitterRequest twitterRequest) {
		this.request = twitterRequest;
	}

	public String getSignatureParameterString() {
		return createOauthParams().createSignatureParamString();
	}

	public String getSignatureBaseString() {
		return createOauthParams().createSignatureBaseString(request.getHttpMethod(), request.getUrl());
	}

	public String getSignature() {
		
        String signingKey = request.getConsumerSecret() + "&" + request.getTokenSecret();
        String signature = Signature.calculateRFC2104HMAC(getSignatureBaseString(), signingKey);
        return signature;

	}
	
	private OauthParams createOauthParams() {
        OauthParams oauthParams = new OauthParams();
        oauthParams.add(new OauthParam("oauth_consumer_key", request.getConsumerKey()));
        oauthParams.add(new OauthParam("oauth_nonce", request.getNonce()));
        oauthParams.add(new OauthParam("oauth_signature_method", request.getSignatureMethod()));
        oauthParams.add(new OauthParam("oauth_timestamp", request.getTimestamp()));
        oauthParams.add(new OauthParam("oauth_version", request.getVersion()));
        
        for (Map.Entry<String, String> entry : request.getOtherParams().entrySet()) {
        	oauthParams.add(new OauthParam(entry.getKey(), entry.getValue()));
        }
        
        return oauthParams;
	}
	
    public String createAuthorizationHeader() {

    	OauthParams oauthParams = createOauthParams();
        oauthParams.add(new OauthParam("oauth_signature", getSignature()));
        return oauthParams.createOathString();
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
                signatureValues.add(param.getKey() + "=" + param.getValue());
            }
            return StringUtils.join(signatureValues, "&");
        }

        public String createSignatureBaseString(String method, String url) {
        	String parameterString = createSignatureParamString();
        	System.out.println(String.format("*** parameter string: [%s] ****", parameterString));
            return String.format("%s&%s&%s", method.toUpperCase(), percentEncode(url), percentEncode(parameterString));
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
            this.key = percentEncode(key);
            this.value = percentEncode(value);
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
            return String.format("%s\"%s\"", key, value);
        }

        public String toSignatureString() {
        	return key + "=" + value;
        }

        @Override
        public int compareTo(OauthParam param) {
            return key.compareTo(param.key);
        }

    }

    public static String percentEncode(String s) {
        if (s == null) {
            return "";
        }
        try {
            return URLEncoder.encode(s, "UTF-8")
                    // OAuth encodes some characters differently:
                    .replace("+", "%20").replace("*", "%2A")
                    .replace("%7E", "~");
            // This could be done faster with more hand-crafted code.
        } catch (UnsupportedEncodingException wow) {
            throw new RuntimeException(wow.getMessage(), wow);
        }
    }    	

}
