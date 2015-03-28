/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Dec 7, 2014
 *
 *********************************************************/
package com.se.common.exception;

/**
 * This object is meant to be declared as final variable, and can be used to set
 * the error status as true inside anonymous block of code.
 * 
 * @author Saurabh Tiwari
 *
 */
public class ErrorObject {

	private boolean isError;
	private Throwable exception;

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

}
