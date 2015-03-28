/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Dec 8, 2014
 *
 *********************************************************/
package com.se.services.enumeration;

import com.se.services.constant.ServiceConstants;

/**
 * Lists out all the System properties that need to be passed
 * as -D parameters.
 * @author Saurabh Tiwari
 *
 */
public enum ServiceSystemProperties {

	BASIC_AUTH_USER(ServiceConstants.BASIC_AUTH_USER), BASIC_AUTH_PASSWORD(ServiceConstants.BASIC_AUTH_PASSWORD);
	private String value;

	ServiceSystemProperties(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isEnvParamMissing(){
		String propValue = System.getProperty(this.value);
		return propValue == null || propValue.equals("");
	}
}
