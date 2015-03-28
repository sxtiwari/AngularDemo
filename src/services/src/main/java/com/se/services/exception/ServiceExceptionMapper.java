package com.se.services.exception;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {

	@Produces(MediaType.APPLICATION_JSON)
	public Response toResponse(ServiceException exception) {
		ServiceExceptionJson exceptionJson = new ServiceExceptionJson();
		exceptionJson.setCode(exception.getCode());
		exceptionJson.setMessage(exception.getMessage());

		Response.Status status = null;
		// TODO statusInt to be calculated from the error code later.
		int errorCode = exception.getCode();
		int statusInt = errorCode/10000;
		status = Response.Status.fromStatusCode(statusInt);

		/*
		 * String requestToken = null; Message message =
		 * PhaseInterceptorChain.getCurrentMessage(); if (message != null)
		 * requestToken = (String) message.getExchange().get(
		 * SaaSFoundationConstants.XKONYREQUESTID);
		 */
		
		exceptionJson.setRequestId(exception.getRequestId());
		return Response.status(status).entity(exceptionJson).build();
	}
}
