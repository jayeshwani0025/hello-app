package com.seller.quickbuy.QuickBuyApp.message.response;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String username;
	private String email;
	private String attribute1;
	
	private Collection<? extends GrantedAuthority> authorities;

	public JwtResponse(String accessToken, String username, String email, String attribute1, Collection<? extends GrantedAuthority> authorities) {
		this.token = accessToken;
		this.username = username;
		this.email = email;
		this.attribute1 = attribute1;
		this.authorities = authorities;
	}

	
	public String getAttribute1() {
		return attribute1;
	}


	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}


	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
	
}