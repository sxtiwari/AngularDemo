/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Dec 8, 2014
 *
 *********************************************************/
package com.se.common.util;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.se.common.enumeration.CommonSystemProperties;

/**
 * @author Saurabh Tiwari
 *
 */

@Component
public class CommonDynamicPropertiesVerifier {

	@PostConstruct
	public void checkForDynamicProperties() {
		
		for(CommonSystemProperties property : CommonSystemProperties.values()){
			if (property.isEnvParamMissing()) {
				throw new Error(
						"System property "
								+ property.getValue()
								+ " is mandatory. Please pass the appropriate -D parameters.");
			}
		}
	}
}
