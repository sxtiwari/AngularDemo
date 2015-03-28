/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Nov 27, 2014 1:25:35 PM 2014
 *
 *********************************************************/

package com.se.common.util;

import org.springframework.stereotype.Component;

import com.se.common.constant.CommonConstants;
/**
 * 
 * @author Saurabh Tiwari
 *
 */
@Component
public class GenericUtil {

	public String getEnvironment() {
		return System.getProperty(CommonConstants.ENVIRONMENT_KEY,
				CommonConstants.DEFAULT_ENVIRONMENT_KEY);
	}

	public String getPropertiesPath() {
		return CommonConstants.PROPERTIES_LOC;
	}
}
