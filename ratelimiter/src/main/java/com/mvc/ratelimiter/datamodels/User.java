package com.mvc.ratelimiter.datamodels;

/**
 * 
 * @author kishore
 * @category model
 *  This is a model object for holding user details, Here user is
 *         a registered user who can get a token and use APi's via Token and can
 *         avail better limit rates.
 *
 */
public class User {

	private String userId;
	private String userToken;
	private long userTokenExpiry;
	private String userName;

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

	public long getUserTokenExpiry() {
		return userTokenExpiry;
	}

	public void setUserTokenExpiry(long userTokenExpiry) {
		this.userTokenExpiry = userTokenExpiry;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userToken=*****, userTokenExpiry=" + userTokenExpiry + ", userName="
				+ userName + "]";
	}

}
