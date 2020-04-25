package com.mvc.ratelimiter.main;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RateLimiterImpl implements RateLimiter {

	@Autowired
	@Qualifier("userRequestsMap")
	java.util.concurrent.ConcurrentHashMap<String, CopyOnWriteArrayList<Long>> userRequestMap;

	@Autowired
	@Qualifier("usagePolicyVoilatingUsers")
	java.util.concurrent.ConcurrentHashMap<String, Long> usagePolicyVoilatingUsers;
	
	@Value("${blueoptima.ratelimiter.time.unit}")
	private String rateLimiterTimeUnit; 

	/**
	 * Checks if user is blocked due to any Dos attack.
	 */
	@Override
	public boolean verifyFairUsageVoilation(String userId) throws Exception {
		long currentTimeInMillis = System.currentTimeMillis();
		if (usagePolicyVoilatingUsers.containsKey(userId)) {
			if (usagePolicyVoilatingUsers.get(userId) < currentTimeInMillis)
				usagePolicyVoilatingUsers.remove(userId);
			else {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * checks if user is eligible for request as per rate limit criteria set by System.
	 */
	@Override
	public boolean verifyIfUserLimitExist(final String userId, final int limit, final long requestTime) throws Exception {
		if (userRequestMap.containsKey(userId)) {
			removeOutSideLimitRequest(userId, requestTime, limit);
			if (userRequestMap.get(userId).size() < limit) {
				CopyOnWriteArrayList<Long> list = userRequestMap.get(userId);
				list.add(requestTime);
				userRequestMap.put(userId, list);
				return true;
			} else
				return false;
		}
		CopyOnWriteArrayList<Long> requestList = new CopyOnWriteArrayList<Long>();
		requestList.add(requestTime);
		userRequestMap.put(userId, requestList);
		return true;
	}

	/**
	 * 
	 * @param userId
	 * @param requestTime
	 * @param limit
	 * Updates List of request timestamps which are not falling in coverage time period for calculating request used within limit time.
	 */
	private void removeOutSideLimitRequest(final String userId, final long requestTime, int limit) {
		List<Long> requestList = userRequestMap.get(userId);
		long coverageTimeinMillies=getLimitCoverageTime(requestTime, limit);
		for (long e : requestList) {
			if (e <= coverageTimeinMillies)
				requestList.remove(e);
			else
				break;
		}

	}

	/**
	 * 
	 * @param requestTime (Time of request recevied at web interceptor)
	 * @return this will return the last time from where we need to calculate the count of request to validate if user havent recahed limit in the fixed duration. 
	 */
	private long getLimitCoverageTime(final long requestTime, int limit) {
		long lastTimeForCalculatingTheNumberOfRequest=1000;
		
		if(rateLimiterTimeUnit.equals("3")) {
			lastTimeForCalculatingTheNumberOfRequest=limit*24*60*60*1000;
		}
		else if(rateLimiterTimeUnit.equals("2"))
			lastTimeForCalculatingTheNumberOfRequest=limit*60*60*1000;
		else if(rateLimiterTimeUnit.equals("1"))
			lastTimeForCalculatingTheNumberOfRequest=limit*60*1000;
		
		return requestTime - lastTimeForCalculatingTheNumberOfRequest;
	}
	
	public void setRateLimiterTimeUnit(String rateLimiterTimeUnit) {
		this.rateLimiterTimeUnit = rateLimiterTimeUnit;
	}

	
}
