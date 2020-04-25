package com.mvc.ratelimiter.exceptions;

public class PolicyViolationException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7670897528772462680L;

	public PolicyViolationException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
