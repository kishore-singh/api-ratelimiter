package com.mvc.ratelimiter.datamodels;

/**
 * 
 * @author kishore
 * Request Model to hold Token Generation Request send by the user
 */

public class TokenRequest {
	String username;
	String password;

	// for json converters
	public TokenRequest() {
		
	}
	public TokenRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "TokenRequest [username=" + username + ", password=Can't LOG ]";
	}

}
