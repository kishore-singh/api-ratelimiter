package com.mvc.ratelimiter.datamodels;

public class Token {

	private String userId;
	private String userToken;
	private long tokenExpiryTime;
	
	public Token(String userId, String userToken, long tokenExpiryTime) {
		super();
		this.userId = userId;
		this.userToken = userToken;
		this.tokenExpiryTime = tokenExpiryTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public long getTokenExpiryTime() {
		return tokenExpiryTime;
	}

	public void setTokenExpiryTime(long tokenExpiryTime) {
		this.tokenExpiryTime = tokenExpiryTime;
	}

	@Override
	public String toString() {
		return "Token [userId=" + userId + ", userToken=*******, tokenExpiryTime=" + tokenExpiryTime + "]";
	}
	
	
}
