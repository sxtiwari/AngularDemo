/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Nov 27, 2014 1:25:35 PM 2014
 *
 *********************************************************/

package com.se.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.se.common.constant.CommonConstants;
import com.se.common.model.PropertiesHolder;
/**
 * 
 * @author Saurabh Tiwari
 *
 */
@Component
public class PropertiesReader {

	private static Properties properties;

	@Autowired
	public void setHolder(PropertiesHolder holder) {
		properties = holder.getProperties();
	}

	public String getStringValue(String key) {
		String property = System.getProperty(key, null);
		if (property == null) {
			property = properties.getProperty(key);
		}
		return property;
	}

	public String getStringValue(String key, String defValue) {
		String property = System.getProperty(key, null);
		if (property == null) {
			property = properties.getProperty(key);

			if (!StringUtils.hasLength(property)) {
				property = defValue;
			}
		}
		return property;
	}

	public List<String> getList(String key) {
		String phrase = System.getProperty(key, null);
		if (phrase == null)
			phrase = properties.getProperty(key);
		List<String> values = new ArrayList<String>();
		if (phrase != null) {
			String[] tokens = phrase.split(CommonConstants.VALUE_DELIMITER);
			for (int i = 0; i < tokens.length; i++) {
				values.add(tokens[i]);
			}
		}
		return values;
	}

	public Integer getIntValue(String key) {
		String property = System.getProperty(key, null);
		if (property == null)
			property = properties.getProperty(key);
		Integer retValue;
		try {
			retValue = Integer.parseInt(property);
		} catch (NumberFormatException ex) {
			retValue = null;
		}
		return retValue;
	}

	public Double getDoubleValue(String key) {
		String property = System.getProperty(key, null);
		if (property == null)
			property = properties.getProperty(key);
		Double retValue;
		try {
			retValue = Double.parseDouble(property);
		} catch (NumberFormatException nfExp) {
			retValue = null;
		}
		return retValue;
	}

	public Boolean getBooleanValue(String key) {
		String property = System.getProperty(key, null);
		Boolean retValue;
		if (property == null)
			property = properties.getProperty(key);
		try {
			retValue = new Boolean(property);
		} catch (Exception e) {
			retValue = null;
		}

		return retValue;
	}

	public Boolean getBooleanValue(String key, boolean defaultVal) {
		String property = System.getProperty(key, null);
		Boolean retValue = defaultVal;
		if (property == null)
			property = properties.getProperty(key);
		try {
			if (property != null)
				retValue = new Boolean(property);
		} catch (Exception e) {
			retValue = defaultVal;
		}
		return retValue;
	}
}
