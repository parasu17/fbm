package com.fbm.mgmt.supervisor.dataobjects;


public class FbmResponse<T> {

	private boolean success;
	private String failureMessage;
	private T responseData;
	
	public FbmResponse(boolean success, String failureMessage, T responseData) {
		this.success = success;
		this.failureMessage = failureMessage;
		this.responseData = responseData;
	}

	public FbmResponse(boolean success) {
		this.success = success;
	}

	public FbmResponse(boolean success, String failureMessage) {
		this.success = success;
		this.failureMessage = failureMessage;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getFailureMessage() {
		return failureMessage;
	}

	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}

	public T getResponseData() {
		return responseData;
	}

	public void setResponseData(T responseData) {
		this.responseData = responseData;
	}
	
}
