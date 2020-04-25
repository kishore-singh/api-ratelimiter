package com.mvc.ratelimiter.exceptions;

public class RateLimitException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4754128886926669296L;
	
	public RateLimitException(String msg) {
		super(msg);
	}

}
