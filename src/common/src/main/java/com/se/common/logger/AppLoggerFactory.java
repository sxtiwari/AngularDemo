package com.se.common.logger;

public class AppLoggerFactory {
	public static AppLogger getLogger() {
		String name = new Exception().getStackTrace()[1].getClassName();
		return new AppLoggerImpl(name);
	}

}
