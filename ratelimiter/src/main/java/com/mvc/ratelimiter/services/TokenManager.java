package com.mvc.ratelimiter.services;

import com.mvc.ratelimiter.datamodels.Token;

public interface TokenManager {

	public Token generateUserToken(final String userId) throws Exception;
	
	public boolean validateUserToken(final String userId, final String userToken) throws Exception;
	
	
}
