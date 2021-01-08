package com.techm.proddiscount.error;

public class ProductsNotPermittedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String code;
	private String message;
	private String reason;

	public ProductsNotPermittedException(String code, String message, String reason) {
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
