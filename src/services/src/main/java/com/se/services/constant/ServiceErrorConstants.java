package com.se.services.constant;

public class ServiceErrorConstants {

	/*
	 * Error code syntax to follow:
	 * 
	 * {HttpErrorCode}{Application Specific Error Code in the order of thousand}
	 * 
	 * eg {400}{0001}
	 * 
	 * #Http Error Codes
	 * 
	 * 500: Internal Server Error
	 * 400: Bad Request
	 * 401: Unauthorized
	 * 403: Forbidden
	 * 
	 */
	
	// Internal Server Error Code
	public static final int CODE_GENERIC_ERROR = 5000001;
	public static final String MSG_GENERIC_ERROR = "requested operation failed";

	public static final int CODE_USER_CREATE_ERROR = 4000001;
	public static final String MSG_USER_CREATE_ERROR = "user could not be created";
	
	public static final int CODE_GUEST_USER_CREATE_ERROR = 4000002;
	public static final String MSG_GUEST_USER_CREATE_ERROR = "guest user could not be created";

	public static final int CODE_USER_SELECT_ERROR = 4000003;
	public static final String MSG_USER_SELECT_ERROR = "error fetching user information";

	public static final int CODE_INVALID_USER_ERROR = 4000004;
	public static final String MSG_INVALID_USER_ERROR = "No matching user found";

	public static final int CODE_USER_SELECT_ALL_ERROR = 4000005;
	public static final String MSG_USER_SELECT_ALL_ERROR = "error fetching user information";
	
	public static final int CODE_USER_SELECT_CRITERIA_ERROR = 4000006;
	public static final String MSG_USER_SELECT_CRITERIA_ERROR = "error fetching user with the given criteria";
	
	public static final int CODE_NO_USER_SELECT_CRITERIA_ERROR = 4000007;
	public static final String MSG_NO_USER_SELECT_CRITERIA_ERROR = "No matching user found with the given criteria";
	
	public static final int CODE_USER_UPDATE_ERROR = 4000008;
	public static final String MSG_USER_UPDATE_ERROR = "user could not be updated";

	public static final int CODE_INVALID_INPUT_PK_ERROR = 4000009;
	public static final String MSG_INVALID_INPUT_PK_ERROR = "only numeric input id values are allowed";
}
