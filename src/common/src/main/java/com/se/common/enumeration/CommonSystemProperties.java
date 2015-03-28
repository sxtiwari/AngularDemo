/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Dec 8, 2014
 *
 *********************************************************/
package com.se.common.enumeration;

import com.se.common.constant.CommonConstants;
import com.se.common.constant.DatabaseProperties;

/**
 * Lists out all the System properties that need to be passed
 * as -D parameters if common.jar is used as a dependency.
 * @author Saurabh Tiwari
 *
 * @since 
 */
public enum CommonSystemProperties {

	DB_HOST(DatabaseProperties.DB_HOST), 
	DB_PORT(DatabaseProperties.DB_PORT), 
	DB_USER(DatabaseProperties.DB_USER), 
	DB_PASSWORD(DatabaseProperties.DB_PASSWORD), 
	LOG_LEVEL(CommonConstants.LOG_LEVEL);

	private String value;

	CommonSystemProperties(String value) {
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
