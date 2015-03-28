/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Dec 8, 2014
 *
 *********************************************************/
package com.se.services.util;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.se.services.enumeration.ServiceSystemProperties;

/**
 * @author Saurabh Tiwari
 *
 */

@Component
public class ServiceDynamicPropertiesVerifier {

	@PostConstruct
	public void checkForDynamicProperties() {
		
		for(ServiceSystemProperties property : ServiceSystemProperties.values()){
			if (property.isEnvParamMissing()) {
				throw new Error(
						"System property "
								+ property.getValue()
								+ " is mandatory. Please pass the appropriate -D parameters.");
			}
		}
	}
}
