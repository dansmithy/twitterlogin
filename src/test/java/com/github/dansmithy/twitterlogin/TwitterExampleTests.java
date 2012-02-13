package com.github.dansmithy.twitterlogin;

import static org.hamcrest.Matchers.*;

import org.junit.Assert;
import org.junit.Test;

public class TwitterExampleTests {

	
	@Test
	public void createSignatureExample() {
		
		String parameterString = "include_entities=true&oauth_consumer_key=xvz1evFS4wEEPTGEFPHBog&oauth_nonce=kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1318622958&oauth_token=370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb&oauth_version=1.0&status=Hello%20Ladies%20%2B%20Gentlemen%2C%20a%20signed%20OAuth%20request%21";
		
		String baseString = "POST&https%3A%2F%2Fapi.twitter.com%2F1%2Fstatuses%2Fupdate.json&include_entities%3Dtrue%26oauth_consumer_key%3Dxvz1evFS4wEEPTGEFPHBog%26oauth_nonce%3DkYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D1318622958%26oauth_token%3D370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb%26oauth_version%3D1.0%26status%3DHello%2520Ladies%2520%252B%2520Gentlemen%252C%2520a%2520signed%2520OAuth%2520request%2521";
		
		String signature = "tnnArxj06cWHq44gCs1OSKk/jLY=";
		
		// see https://dev.twitter.com/docs/auth/creating-signature
	    TwitterRequest request = new TwitterRequest()
	    	.setConsumerKey("xvz1evFS4wEEPTGEFPHBog")
	    	.setConsumerSecret("kAcSOqF21Fu85e7zjz7ZN2U4ZRhfV3WpwPAoE3Z7kBw")
	    	.setNonce("kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg")
	    	.setTimestamp("1318622958")
	    	.setTokenSecret("LswwdoUaIvS8ltyTt5jkRh4J50vUPVVHtR2YPi5kE")
	    	.setUrl("https://api.twitter.com/1/statuses/update.json")
	    	.addParam(TwitterRequest.KEY_TOKEN, "370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb")
	    	.addParam("status", "Hello Ladies + Gentlemen, a signed OAuth request!")
	    	.addParam("include_entities", "true")
        	;	    	
	    TwitterOauthBuilder builder = new TwitterOauthBuilder(request);
	    
	    Assert.assertThat(builder.getSignatureParameterString(), is(equalTo(parameterString)));
	    Assert.assertThat(builder.getSignatureBaseString(), is(equalTo(baseString)));
	    Assert.assertThat(builder.getSignature(), is(equalTo(signature)));
	}
	
	@Test
	public void createAuthRequestExample() {
		
		String parameterString = "include_entities=true&oauth_consumer_key=xvz1evFS4wEEPTGEFPHBog&oauth_nonce=kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1318622958&oauth_token=370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb&oauth_version=1.0&status=Hello%20Ladies%20%2B%20Gentlemen%2C%20a%20signed%20OAuth%20request%21";
		
		String baseString = "POST&https%3A%2F%2Fapi.twitter.com%2F1%2Fstatuses%2Fupdate.json&include_entities%3Dtrue%26oauth_consumer_key%3Dxvz1evFS4wEEPTGEFPHBog%26oauth_nonce%3DkYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D1318622958%26oauth_token%3D370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb%26oauth_version%3D1.0%26status%3DHello%2520Ladies%2520%252B%2520Gentlemen%252C%2520a%2520signed%2520OAuth%2520request%2521";
		
		String signature = "tnnArxj06cWHq44gCs1OSKk/jLY=";
		
	    // see https://dev.twitter.com/docs/auth/authorizing-request
        TwitterRequest request = new TwitterRequest()
	    	.setConsumerKey("cChZNFj6T5R0TigYB9yd1w")
	    	.setConsumerSecret("kAcSOqF21Fu85e7zjz7ZN2U4ZRhfV3WpwPAoE3Z7kBw")
	    	.setTokenSecret("LswwdoUaIvS8ltyTt5jkRh4J50vUPVVHtR2YPi5kE")
	    	.setNonce("ea9ec8429b68d6b77cd5600adbbb0456")
	    	.setTimestamp("1318467427")
	    	.setUrl("https://api.twitter.com/1/statuses/update.json")
	    	.addParam(TwitterRequest.KEY_CALLBACK, "http://localhost/sign-in-with-twitter/");
        
	    	;  
    	
	    TwitterOauthBuilder builder = new TwitterOauthBuilder(request);
	    
	    Assert.assertThat(builder.getSignatureParameterString(), is(equalTo(parameterString)));
	    Assert.assertThat(builder.getSignatureBaseString(), is(equalTo(baseString)));
	    Assert.assertThat(builder.getSignature(), is(equalTo(signature)));
	}	
}
