/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Dec 21, 2014
 *
 *********************************************************/
package com.se.common.util;

import java.util.Date;

import org.joda.time.DateTime;

/**
 * @author Saurabh Tiwari
 *
 */
public class DateUtil {

	public static Date getCurrentDate(){
		DateTime jodaDateTime = new DateTime();
		return jodaDateTime.toDate();
	}
}
