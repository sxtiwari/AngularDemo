/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Dec 10, 2014
 *
 *********************************************************/
package com.se.common.util;

import org.apache.commons.lang.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * @author Saurabh Tiwari
 *
 */

@Component
public class PasswordEncryptionUtil {

	public String generateSalt(){
		return BCrypt.gensalt(12);
	}
	
	public String encryptPassword(String password, String salt){
		return BCrypt.hashpw(password, salt);
	}
	
	public boolean matches(String password, String hashedPassword){
		return BCrypt.checkpw(password, hashedPassword);
	}
	
	public String generateRandomPassword(){
		return RandomStringUtils.randomAscii(10);
	}
}
