package com.techm.proddiscount.error;

public class CustomErrorResponse {

	private String code;
	private String message;
	private String reason;

	public CustomErrorResponse(String code, String message, String reason) {
		this.code = code;
		this.message = message;
		this.reason = reason;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
