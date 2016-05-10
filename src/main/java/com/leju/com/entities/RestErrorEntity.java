package com.leju.com.entities;

public class RestErrorEntity {
	private String error_code;
	private String error_message;
	private String exception;
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	private String Url;
	
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}

	

}
