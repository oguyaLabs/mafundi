package com.wglxy.example.dashL.model;

public class APIMessage {
	
	int status;
	String statusMessage;

	public APIMessage() {}
	
	public APIMessage(int status, String statusMessage) {
		this.status = status;
		this.statusMessage = statusMessage;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

}
