package com.se.common.logger;

import java.util.Arrays;

import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

import ch.qos.logback.classic.Logger;

import com.se.common.util.RequestContext;

/**
 * 
 * @author Saurabh Tiwari
 *
 */
public class AppLoggerImpl implements AppLogger {

	/*
	 * * To change the logging level at run time, use the following
	 * 
	 * Logger root = Logger.getRootLogger(); Enumeration allLoggers =
	 * root.getLoggerRepository().getCurrentCategories();
	 * 
	 * //set logging level of root and all logging instances in the system if
	 * ("FATAL".equalsIgnoreCase(logLevel)) { root.setLevel(Level.FATAL); while
	 * (allLoggers.hasMoreElements()){ Category tmpLogger = (Category)
	 * allLoggers.nextElement(); tmpLogger .setLevel(Level.FATAL); } }else if
	 * ("ERROR".equalsIgnoreCase(logLevel)) { root.setLevel(Level.ERROR); while
	 * (allLoggers.hasMoreElements()){ Category tmpLogger = (Category)
	 * allLoggers.nextElement(); tmpLogger .setLevel(Level.ERROR); } }
	 */
	
	private Logger logger;
	public static final String FQCN = Logger.class.getName();

	public AppLoggerImpl(String name) {
		this.logger = (Logger) LoggerFactory.getLogger(name);
	}

	public void error(String message, Object... params) {
		log(LocationAwareLogger.ERROR_INT, null, message, params);
	}

	public void error(RequestContext context, String message, Object... params) {
		log(LocationAwareLogger.ERROR_INT, context, message, params);
	}

	public void info(String message, Object... params) {
		log(LocationAwareLogger.INFO_INT, null, message, params);
	}

	public void info(RequestContext context, String message, Object... params) {
		log(LocationAwareLogger.INFO_INT, context, message, params);
	}

	public void warn(String message, Object... params) {
		log(LocationAwareLogger.WARN_INT, null, message, params);
	}

	public void warn(RequestContext context, String message, Object... params) {
		log(LocationAwareLogger.WARN_INT, context, message, params);
	}

	public void debug(String message, Object... params) {
		log(LocationAwareLogger.DEBUG_INT, null, message, params);
	}

	public void debug(RequestContext context, String message, Object... params) {
		log(LocationAwareLogger.DEBUG_INT, context, message, params);
	}

	public void trace(String message, Object... params) {
		log(LocationAwareLogger.TRACE_INT, null, message, params);
	}

	public void trace(RequestContext context, String message, Object... params) {
		log(LocationAwareLogger.TRACE_INT, context, message, params);
	}

	private void log(int level, RequestContext requestContext, String message,
			Object... params) {
		String prefix = "SE-";
		Throwable t = null;
		if (params.length > 0 && params[params.length - 1] instanceof Throwable) {
			t = (Throwable) params[params.length - 1];
			if (params.length > 1) {
				params = Arrays.copyOfRange(params, 0, params.length - 1);
			} else {
				params = new Object[0];
			}
		}

		if (requestContext != null) {
			if (requestContext.getRequestToken() != null)
				prefix += "RequestToken=[" + requestContext.getRequestToken()
						+ "] ";
		}

		message = prefix + message;
		logger.log(null, FQCN, level, message, params, t);
	}
}
