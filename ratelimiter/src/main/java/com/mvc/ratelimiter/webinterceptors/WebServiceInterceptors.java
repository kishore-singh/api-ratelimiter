package com.mvc.ratelimiter.webinterceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mvc.ratelimiter.datamodels.Token;
import com.mvc.ratelimiter.exceptions.PolicyViolationException;
import com.mvc.ratelimiter.exceptions.RateLimitException;
import com.mvc.ratelimiter.main.RateLimiterImpl;

public class WebServiceInterceptors extends HandlerInterceptorAdapter {

	@Autowired
	@Qualifier("userTokenMap")
	java.util.concurrent.ConcurrentHashMap<String, Token> userTokenMap;

	@Autowired
	RateLimiterImpl blueoptimaRateLimiter;

	@Value("${blueoptima.guest.user.request.limit}")
	private String guestUserLimit;

	@Value("${blueoptima.auth.user.request.limit}")
	private String blueoptimauserLimit;

	private static final Logger logger = LoggerFactory.getLogger(WebServiceInterceptors.class);
	private static final String KEY_FOR_USER_NAME = "username";
	private static final String KEY_FOR_TOKEN = "usertoken";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("Request at Web Interceptor requestBody {}",request);
		final long requestTime = System.currentTimeMillis();
		String userId = "";
		int limit = Integer.parseInt(guestUserLimit);
		if (request != null) {
			userId = request.getHeader("X-FORWARDED-FOR");
			if (userId == null || "".equals(userId)) {
				userId = request.getRemoteAddr();
			}
		}
		if (request.getHeader(KEY_FOR_USER_NAME) != null || request.getHeader(KEY_FOR_TOKEN) != null) {
			userId = request.getHeader(KEY_FOR_USER_NAME);
			limit = Integer.parseInt(blueoptimauserLimit);
			if (!userTokenMap.containsKey(request.getHeader(KEY_FOR_USER_NAME))
					|| !userTokenMap.get(request.getHeader(KEY_FOR_USER_NAME)).getUserToken()
							.equals(request.getHeader(KEY_FOR_TOKEN))
					|| !(userTokenMap.get(request.getHeader(KEY_FOR_USER_NAME)).getTokenExpiryTime() < requestTime)) {
				throw new Exception("Invalid / Expired Userkey/userToken passed!");

			}
		}
		if (blueoptimaRateLimiter.verifyFairUsageVoilation(userId))
			throw new PolicyViolationException("Policy Violeted");
		if (!blueoptimaRateLimiter.verifyIfUserLimitExist(userId, limit, requestTime))
			throw new RateLimitException("Rate Limit breach by user!");
		request.setAttribute("startTime", requestTime);
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long startTime = (Long) request.getAttribute("startTime");
		logger.info("Request URL::" + request.getRequestURL().toString() + ":: End Time=" + System.currentTimeMillis());
		logger.info("Request URL::" + request.getRequestURL().toString() + ":: Time Taken="
				+ (System.currentTimeMillis() - startTime));
	}

}
