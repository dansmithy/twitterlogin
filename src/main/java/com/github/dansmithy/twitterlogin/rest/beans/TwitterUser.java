package com.github.dansmithy.twitterlogin.rest.beans;

import java.util.Arrays;
import java.util.Collection;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.scribe.model.Token;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TwitterUser implements UserDetails {

	private String username;
	private Token accessToken;
	private String[] roles = { "user" };

	public static TwitterUser EMPTY_USER = new TwitterUser(null, null);
	
	public TwitterUser(String username, Token accessToken) {
		super();
		this.username = username;
		this.accessToken = accessToken;
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
	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

	@JsonIgnore
	public Token getAccessToken() {
		return accessToken;
	}

	public String[] getRoles() {
		return roles;
	}

}