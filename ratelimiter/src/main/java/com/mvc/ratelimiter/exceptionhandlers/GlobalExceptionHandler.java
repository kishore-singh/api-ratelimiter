package com.mvc.ratelimiter.exceptionhandlers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mvc.ratelimiter.datamodels.ResponseWrapper;
import com.mvc.ratelimiter.datamodels.StatusBean;
import com.mvc.ratelimiter.exceptions.PolicyViolationException;
import com.mvc.ratelimiter.exceptions.RateLimitException;

@EnableWebMvc
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@Value("${blueoptima.support.email.id}")
	private String supportEmailId;
	
	@ExceptionHandler(PolicyViolationException.class)
	public @ResponseBody ResponseWrapper<String> usagePolicyVoilationExceptionHandler(HttpServletRequest request,
			PolicyViolationException ex) {
		ResponseWrapper<String> response = new ResponseWrapper<String>();
		response.setStatus(new StatusBean(200, "You are blocked due to violating usage policies!"));
		ex.printStackTrace();
		return response;
	}

	@ExceptionHandler(RateLimitException.class)
	public @ResponseBody ResponseWrapper<String> rateLimitExceptionHandler(HttpServletRequest request,
			RateLimitException ex) {
		ResponseWrapper<String> response = new ResponseWrapper<String>();
		response.setStatus(new StatusBean(200, "You have exceeded rate limit, please try after sometime!"));
		ex.printStackTrace();
		return response;
	}
	
	@ExceptionHandler(Exception.class)
	public @ResponseBody ResponseWrapper<String> exceptionHandlerMethod(HttpServletRequest request, Exception ex) {
		ResponseWrapper<String> response = new ResponseWrapper<String>();
		response.setStatus(
				new StatusBean(200, "Problem occured at server (" + ex.getMessage() + "), please contact to support team "+ supportEmailId + " ."));
		ex.printStackTrace();
		return response;
	}

	@ExceptionHandler(Error.class)
	public @ResponseBody ResponseWrapper<String> errorHandlerMethod(HttpServletRequest request, Exception ex) {
		ResponseWrapper<String> response = new ResponseWrapper<String>();
		response.setStatus(new StatusBean(200,
				"Unknown erro occured at server (" + ex.getMessage() + "), please contact to blueoptima support team " + supportEmailId + " ."));
		ex.printStackTrace();
		return response;
	}
	
	

}
