/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Dec 3, 2014
 *
 *********************************************************/
package com.se.common.exception;

/**
 * DataException to be thrown by all the DAOs.
 * @author Saurabh Tiwari
 *
 */
public class DataException extends Exception {

	private static final long serialVersionUID = 00000000003L;
	private int code;

	public DataException() {
		super();
	}

	public DataException(Throwable e){
		super(e);
	}
	
	public DataException(int code, String message) {
		super(message);
		this.code = code;
	}

	public DataException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
