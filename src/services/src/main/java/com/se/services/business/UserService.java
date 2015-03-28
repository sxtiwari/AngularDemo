package com.se.services.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.se.common.dao.GenericDao;
import com.se.common.data.model.UserDataModel;
import com.se.common.exception.DataException;
import com.se.common.logger.AppLogger;
import com.se.common.logger.AppLoggerFactory;
import com.se.common.util.DateUtil;
import com.se.common.util.PasswordEncryptionUtil;
import com.se.common.util.RequestContext;
import com.se.services.constant.ServiceErrorConstants;
import com.se.services.exception.ExceptionUtil;
import com.se.services.exception.ServiceException;

@Service
public class UserService {
	@Autowired
	private GenericDao<UserDataModel> genericDao;
	@Autowired
	private ExceptionUtil exceptionUtil;
	@Autowired
	private PasswordEncryptionUtil passwordUtil;

	private static AppLogger logger = AppLoggerFactory.getLogger();

	public UserDataModel createUser(UserDataModel user, RequestContext context)
			throws ServiceException {
		String salt = null;
		String hashedPassword = null;
		try {
			salt = passwordUtil.generateSalt();
			hashedPassword = passwordUtil.encryptPassword(user.getPassword(),
					salt);
			user.setEmail(user.getEmail());
			user.setPassword(hashedPassword);
			user.setCreatedDate(DateUtil.getCurrentDate());
			return genericDao.create(user, UserDataModel.class, context);
		} catch (DataException e) {
			logger.error(context, "Create user failed", e);
			throw exceptionUtil.transform(new DataException(
					ServiceErrorConstants.CODE_USER_CREATE_ERROR,
					ServiceErrorConstants.MSG_USER_CREATE_ERROR, e), context);
		} catch (Exception ex) {
			logger.error(context, "Create user failed", ex);
			throw exceptionUtil.transform(new DataException(
					ServiceErrorConstants.CODE_USER_CREATE_ERROR,
					ServiceErrorConstants.MSG_USER_CREATE_ERROR, ex), context);
		}
	}

	public UserDataModel createGuestUser(UserDataModel user,
			RequestContext context) throws ServiceException {
		String salt = null;
		String randomPassword = null;
		String hashedPassword = null;
		try {
			salt = passwordUtil.generateSalt();
			randomPassword = passwordUtil.generateRandomPassword();
			hashedPassword = passwordUtil.encryptPassword(randomPassword, salt);

			user.setEmail(user.getEmail());
			user.setGuest(true);
			user.setPassword(hashedPassword);
			user.setCreatedDate(DateUtil.getCurrentDate());
			return genericDao.create(user, UserDataModel.class, context);
		} catch (DataException e) {
			logger.error(context, "Create guest user failed", e);
			throw exceptionUtil.transform(e, context);
		} catch (Exception ex) {
			logger.error(context, "Create guest user failed", ex);
			throw exceptionUtil.transform(new DataException(
					ServiceErrorConstants.CODE_GUEST_USER_CREATE_ERROR,
					ServiceErrorConstants.MSG_GUEST_USER_CREATE_ERROR, ex),
					context);
		}
	}

	public UserDataModel getUser(int pk, RequestContext context)
			throws ServiceException {
		try {
			UserDataModel user = genericDao.getByPK(pk, UserDataModel.class,
					context);
			if (user != null)
				return user;
			else {
				throw exceptionUtil.transform(new DataException(
						ServiceErrorConstants.CODE_INVALID_USER_ERROR,
						ServiceErrorConstants.MSG_INVALID_USER_ERROR), context);
			}
		} catch (ServiceException e) {
			logger.error(context, "Select user failed", e);
			throw e;
		} catch (DataException e) {
			logger.error(context, "Select user failed", e);
			throw exceptionUtil.transform(new DataException(
					ServiceErrorConstants.CODE_USER_SELECT_ERROR,
					ServiceErrorConstants.MSG_USER_SELECT_ERROR, e), context);
		} catch (Exception ex) {
			logger.error(context, "select user failed", ex);
			throw exceptionUtil.transform(new DataException(
					ServiceErrorConstants.CODE_USER_SELECT_ERROR,
					ServiceErrorConstants.MSG_USER_SELECT_ERROR, ex), context);
		}
	}

	public List<UserDataModel> getAllUsers(RequestContext context)
			throws ServiceException {
		try {
			List<UserDataModel> users = genericDao.getAll(UserDataModel.class,
					context);
			return users;
		} catch (DataException e) {
			logger.error(context, "Select all users failed", e);
			throw exceptionUtil.transform(new DataException(
					ServiceErrorConstants.CODE_USER_SELECT_ALL_ERROR,
					ServiceErrorConstants.MSG_USER_SELECT_ALL_ERROR, e),
					context);
		} catch (Exception ex) {
			logger.error(context, "select all users failed", ex);
			throw exceptionUtil.transform(new DataException(
					ServiceErrorConstants.CODE_USER_SELECT_ALL_ERROR,
					ServiceErrorConstants.MSG_USER_SELECT_ALL_ERROR, ex),
					context);
		}
	}

	public List<UserDataModel> getUsers(UserDataModel model,
			RequestContext context) throws ServiceException {
		try {
			List<UserDataModel> users = genericDao.get(UserDataModel.class,
					model, context);
			if (users != null && users.size() > 0)
				return users;
			else
				throw exceptionUtil
						.transform(
								new DataException(
										ServiceErrorConstants.CODE_NO_USER_SELECT_CRITERIA_ERROR,
										ServiceErrorConstants.MSG_NO_USER_SELECT_CRITERIA_ERROR),
								context);

		} catch (DataException e) {
			logger.error(context, "get users failed", e);
			throw exceptionUtil.transform(new DataException(
					ServiceErrorConstants.CODE_USER_SELECT_CRITERIA_ERROR,
					ServiceErrorConstants.MSG_USER_SELECT_CRITERIA_ERROR, e),
					context);
		} catch (Exception ex) {
			logger.error(context, "get users failed", ex);
			throw exceptionUtil.transform(new DataException(
					ServiceErrorConstants.CODE_USER_SELECT_CRITERIA_ERROR,
					ServiceErrorConstants.MSG_USER_SELECT_CRITERIA_ERROR, ex),
					context);
		}
	}

	public UserDataModel updateUser(int pk, UserDataModel model,
			RequestContext context) throws ServiceException {
		try {
			model.setLastModifiedDate(DateUtil.getCurrentDate());
			UserDataModel user = genericDao.updateByPK(pk, model,
					UserDataModel.class, context);
			if (user != null)
				return user;
			else {
				throw exceptionUtil.transform(new DataException(
						ServiceErrorConstants.CODE_INVALID_USER_ERROR,
						ServiceErrorConstants.MSG_INVALID_USER_ERROR), context);
			}
		} catch (ServiceException e) {
			logger.error(context, "Update user failed", e);
			throw e;
		} catch (DataException e) {
			logger.error(context, "Update user failed", e);
			throw exceptionUtil.transform(new DataException(
					ServiceErrorConstants.CODE_USER_UPDATE_ERROR,
					ServiceErrorConstants.MSG_USER_UPDATE_ERROR, e), context);
		} catch (Exception ex) {
			logger.error(context, "Update user failed", ex);
			throw exceptionUtil.transform(new DataException(
					ServiceErrorConstants.CODE_USER_UPDATE_ERROR,
					ServiceErrorConstants.MSG_USER_UPDATE_ERROR, ex), context);
		}
	}

}
