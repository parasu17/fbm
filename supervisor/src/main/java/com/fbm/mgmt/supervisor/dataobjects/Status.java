package com.fbm.mgmt.supervisor.dataobjects;

public class Status {

	private String statusMessage;
	private int statusCode;

	public Status(int statusCode, String statusMessage) {
		this.statusMessage = statusMessage;
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
