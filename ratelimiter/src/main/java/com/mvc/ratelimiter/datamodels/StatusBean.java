/**
 * 
 */
package com.mvc.ratelimiter.datamodels;

/**
 * @author kishore
 *
 */
public class StatusBean {

	public static final StatusBean STATUS_SUCCESS = new StatusBean(200,"Success");
	
	private int statusCode;
	private String statusMessage;
	
	public StatusBean(int statusCode, String statusMessage) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}
	
	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}
	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * @return the statusMessage
	 */
	public String getStatusMessage() {
		return statusMessage;
	}
	/**
	 * @param statusMessage the statusMessage to set
	 */
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	
}
