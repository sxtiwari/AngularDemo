package com.se.services.exception;

public class ServiceException extends Exception {
	private static final long serialVersionUID = 0000000000005L;
	private int code;
	private String requestId;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(int code, String message) {
		super(message);
		this.code = code;
	}

	public ServiceException(int code, String message, String requestId) {
		super(message);
		this.code = code;
		this.requestId = requestId;
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public ServiceException(int code, String message, String requestId, Throwable cause) {
		super(message, cause);
		this.code = code;
		this.requestId = requestId;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
