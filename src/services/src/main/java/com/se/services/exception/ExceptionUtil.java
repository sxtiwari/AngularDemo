package com.se.services.exception;

import org.springframework.stereotype.Component;

import com.se.common.exception.DataException;
import com.se.common.util.RequestContext;

@Component
public class ExceptionUtil {

	public ServiceException transform(DataException dataException,
			RequestContext context) {
		return new ServiceException(dataException.getCode(),
				dataException.getMessage(), context.getRequestToken(), dataException.getCause());
	}
}
