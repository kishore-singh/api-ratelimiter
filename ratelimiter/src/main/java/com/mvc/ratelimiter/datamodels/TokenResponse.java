package com.mvc.ratelimiter.datamodels;

/**
 * 
 * @author kishore
 * This response Model will be used for sending Token Generation Response 
 * This will be used with the Response Wrapper for sending out http response 
 *
 */

public class TokenResponse {
	String username;
	String token;

	public TokenResponse(String username, String token) {
		super();
		this.username = username;
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "TokenRequest [username=" + username + ", token=Can't LOG ]";
	}

}
