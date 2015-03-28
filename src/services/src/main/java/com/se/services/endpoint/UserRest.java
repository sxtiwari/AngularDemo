/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Nov 27, 2014 1:25:35 PM 2014
 *
 *********************************************************/

package com.se.services.endpoint;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.reflect.TypeToken;
import com.se.common.data.model.UserDataModel;
import com.se.common.logger.AppLogger;
import com.se.common.logger.AppLoggerFactory;
import com.se.common.util.JsonUtil;
import com.se.common.util.RequestContext;
import com.se.services.business.UserService;
import com.se.services.constant.ServiceErrorConstants;
import com.se.services.exception.ExceptionUtil;
import com.se.services.exception.ServiceException;
import com.se.services.util.ServiceUtil;

/**
 * 
 * @author Saurabh Tiwari
 *
 */
@WebService
public class UserRest {

	@Autowired
	private UserService userService;
	@Autowired
	private ExceptionUtil exceptionUtil;
	@Autowired
	private ServiceUtil serviceUtil;

	private static AppLogger logger = AppLoggerFactory.getLogger();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(UserDataModel user) throws ServiceException {
		RequestContext context = null;
		try {
			context = serviceUtil.getRequestContext();
			UserDataModel createdUser = userService.createUser(user, context);
			return Response.ok(createdUser).build();
		} catch (ServiceException e) {
			logger.error(context, "Create user failed", e);
			throw e;
		} catch (Exception e) {
			logger.error(context, "Create user failed", e);
			throw new ServiceException(
					ServiceErrorConstants.CODE_GENERIC_ERROR,
					ServiceErrorConstants.MSG_GENERIC_ERROR, e);
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/guest")
	public Response createGuestUser(UserDataModel user) throws ServiceException {
		RequestContext context = null;
		try {
			context = serviceUtil.getRequestContext();
			logger.debug("Sample Logger");
			UserDataModel createdUser = userService.createGuestUser(user,
					context);
			return Response.ok(createdUser).build();
		} catch (ServiceException e) {
			logger.error(context, "Create guest user failed", e);
			throw e;
		} catch (Exception e) {
			logger.error(context, "Create guest user failed", e);
			throw new ServiceException(
					ServiceErrorConstants.CODE_GENERIC_ERROR,
					ServiceErrorConstants.MSG_GENERIC_ERROR, e);
		}
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers(@QueryParam("q") UserDataModel model)
			throws ServiceException {
		RequestContext context = null;
		List<UserDataModel> users = null;
		try {
			context = serviceUtil.getRequestContext();
			if (model != null)
				users = userService.getUsers(model, context);
			else
				users = userService.getAllUsers(context);
			String json = JsonUtil.toJson(users,
					new TypeToken<List<UserDataModel>>() {
					}.getType());
			return Response.ok(json).build();
		} catch (ServiceException e) {
			logger.error(context, "get all users failed", e);
			throw e;
		} catch (Exception e) {
			logger.error(context, "get all users failed", e);
			throw new ServiceException(
					ServiceErrorConstants.CODE_GENERIC_ERROR,
					ServiceErrorConstants.MSG_GENERIC_ERROR, e);
		}
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{pk}")
	public Response getUser(@PathParam("pk") String pk) throws ServiceException {
		RequestContext context = null;
		try {
			context = serviceUtil.getRequestContext();
			int id = Integer.parseInt(pk);
			UserDataModel user = userService.getUser(id, context);
			return Response.ok(user).build();
		} catch (ServiceException e) {
			logger.error(context, "get user failed", e);
			throw e;
		} catch (NumberFormatException e) {
			logger.error(context, "get user failed", e);
			throw new ServiceException(
					ServiceErrorConstants.CODE_INVALID_INPUT_PK_ERROR,
					ServiceErrorConstants.MSG_INVALID_INPUT_PK_ERROR, e);
		} catch (Exception e) {
			logger.error(context, "get user failed", e);
			throw new ServiceException(
					ServiceErrorConstants.CODE_GENERIC_ERROR,
					ServiceErrorConstants.MSG_GENERIC_ERROR, e);
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{pk}")
	public Response updateUser(@PathParam("pk") String pk, UserDataModel user)
			throws ServiceException {
		RequestContext context = null;
		try {
			context = serviceUtil.getRequestContext();
			int id = Integer.parseInt(pk);
			UserDataModel updatedUser = userService.updateUser(id, user,
					context);
			return Response.ok(updatedUser).build();
		} catch (ServiceException e) {
			logger.error(context, "update user failed", e);
			throw e;
		} catch (NumberFormatException e) {
			logger.error(context, "update user failed", e);
			throw new ServiceException(
					ServiceErrorConstants.CODE_INVALID_INPUT_PK_ERROR,
					ServiceErrorConstants.MSG_INVALID_INPUT_PK_ERROR, e);
		} catch (Exception e) {
			logger.error(context, "update user failed", e);
			throw new ServiceException(
					ServiceErrorConstants.CODE_GENERIC_ERROR,
					ServiceErrorConstants.MSG_GENERIC_ERROR, e);
		}
	}

}
