/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Dec 3, 2014
 *
 *********************************************************/
package com.se.common.constant;

/**
 * Lists all the error codes and messages.
 * @author Saurabh Tiwari
 *
 */
public class ErrorConstants {
	
	public static final int CODE_ERROR_BUILDING_QUERY = 101;
	public static final String MSG_ERROR_BUILDING_QUERY = "Sql generation failed with errors.";
	
	public static final int CODE_ERROR_INSERT = 102;
	public static final String MSG_ERROR_INSERT = "Sql insert failed with errors.";

	public static final int CODE_ERROR_SELECT = 103;
	public static final String MSG_ERROR_SELECT = "Sql select failed with errors.";
	
	public static final int CODE_ERROR_UPDATE = 104;
	public static final String MSG_ERROR_UPDATE = "Sql update failed with errors.";

	public static final int CODE_ERROR_DELETE = 105;
	public static final String MSG_ERROR_DELETE = "Sql delete failed with errors.";

}
