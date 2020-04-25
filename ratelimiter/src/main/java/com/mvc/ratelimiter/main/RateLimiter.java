package com.mvc.ratelimiter.main;

public interface RateLimiter {

	public boolean verifyFairUsageVoilation(final String userId) throws Exception;
	
	public boolean verifyIfUserLimitExist(final String userId, final int limit, final long requestTime) throws Exception;
	
}
