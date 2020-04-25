/**
 * 
 */
package com.mvc.ratelimiter.datamodels;

/**
 * @author kishore
 * This is Generic Response Wrapper will be used for PReparing http responses
 * Generic Object result will be the actual Response depending upon API invocation 
 *
 */
public class ResponseWrapper<T> {

	private StatusBean status;
	private T result;
	/**
	 * @return the status
	 */
	public StatusBean getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(StatusBean status) {
		this.status = status;
	}
	/**
	 * @return the result
	 */
	public T getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(T result) {
		this.result = result;
	}
	
	
}
