package com.github.dansmithy.twitterlogin.model;

import java.util.Arrays;
import java.util.Collection;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TwitterUser implements Authentication {

	public static TwitterUser EMPTY_USER = new TwitterUser(null, null, new String[0]);
	
	public static final String ROLE_USER = "user";
	public static final String ROLE_ADMIN = "admin";
	
	private String username;
	private OAuthToken accessToken;
	private final String[] roles;

	public TwitterUser(String username, OAuthToken accessToken, String[] roles) {
		super();
		this.username = username;
		this.accessToken = accessToken;
		this.roles = roles;
	}
	

	@JsonIgnore
	@Override
	public Collection<GrantedAuthority> getAuthorities() {

		return Collections2.transform(Arrays.asList(getRoles()), new Function<String, GrantedAuthority>() {

			@Override
			public GrantedAuthority apply(String role) {
				return createGrantedAuthority(role);
			}

		});
	}

	private GrantedAuthority createGrantedAuthority(String role) {
		return new GrantedAuthorityImpl(role);
	}

	@JsonIgnore
	public OAuthToken getAccessToken() {
		return accessToken;
	}

	public String[] getRoles() {
		return roles;
	}

	@JsonProperty("username")
	@Override
	public String getName() {
		return username;
	}

	@JsonIgnore
	@Override
	public Object getCredentials() {
		return getAccessToken();
	}

	@JsonIgnore
	@Override
	public Object getDetails() {
		return null;
	}

	@JsonIgnore
	@Override
	public Object getPrincipal() {
		return username;
	}

	@JsonIgnore
	@Override
	public boolean isAuthenticated() {
		return getRoles().length > 0;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		throw new UnsupportedOperationException("Cannot change the authenticated status");
	}
	
	public static class PotentialTwitterUser extends TwitterUser {

		private static final String ANONYMOUS_ROLE = "ANONYMOUS";

		public PotentialTwitterUser(OAuthToken requestToken) {
			super(null, requestToken, new String[] { ANONYMOUS_ROLE });
			
		}
	}

}