/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Nov 27, 2014 1:25:35 PM 2014
 *
 *********************************************************/

package com.se.services.util;

import javax.ws.rs.core.Response;

import org.apache.cxf.phase.PhaseInterceptorChain;
import org.springframework.stereotype.Component;

import com.se.common.util.RequestContext;
import com.se.services.enumeration.ServiceHeaders;

/**
 * 
 * @author Saurabh Tiwari
 *
 */

@Component
public class ServiceUtil {

	public Response getDefaultSuccessResponse() {
		return Response.ok().build();
	}

	public Response getDefaultErrorResponse() {
		return Response.serverError().build();
	}

	public Response getSuccessResponse() {
		return null;
	}

	public Response getErrorResponse() {
		return null;
	}

	public RequestContext getRequestContext() {
		return (RequestContext) PhaseInterceptorChain.getCurrentMessage()
				.getExchange()
				.get(ServiceHeaders.REQUEST_TOKEN_HEADER.getName());
	}
}
