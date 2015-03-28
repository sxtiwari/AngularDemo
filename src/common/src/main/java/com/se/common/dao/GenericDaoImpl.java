/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Dec 3, 2014
 *
 *********************************************************/
package com.se.common.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;
import com.se.common.constant.ErrorConstants;
import com.se.common.data.model.GenericDataModel;
import com.se.common.enumeration.SQLOperator;
import com.se.common.exception.DataException;
import com.se.common.exception.ErrorObject;
import com.se.common.logger.AppLogger;
import com.se.common.logger.AppLoggerFactory;
import com.se.common.model.ColumnDto;
import com.se.common.model.CriteriaDto;
import com.se.common.util.RequestContext;
import com.se.common.util.SqlUtil;

/**
 * Generic DAO to perform CRUD operations. This class must be autowired by all
 * the DAOs.
 * 
 * @author Saurabh Tiwari
 *
 */

@Repository
public class GenericDaoImpl<T extends GenericDataModel> implements
		GenericDao<T> {

	protected JdbcTemplate jdbcTemplate;

	@Autowired
	public void init(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Autowired
	private SqlUtil sqlUtil;
	private static AppLogger logger = AppLoggerFactory.getLogger();

	public T create(T model, Class<T> type, RequestContext context)
			throws DataException {
		logger.info(context, "Create called for type {}", type.getName());
		try {
			final ErrorObject error = new ErrorObject();
			final List<ColumnDto> columns = sqlUtil.getColumnData(type, model);
			final String insertQuery = sqlUtil.insertPrepareStatement(columns,
					type);
			logger.info(context, "insert query {}", insertQuery);
			logger.debug(context, "Create request for model{}", model);

			KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement statement = conn.prepareStatement(
							insertQuery, Statement.RETURN_GENERATED_KEYS);
					for (int i = 0; i < columns.size(); i++) {
						ColumnDto column = columns.get(i);
						Object value = column.getColumnValue();
						int sqlType = column.getColumnType();
						sqlUtil.setStatementValue(i + 1, sqlType, value,
								statement, error);
					}

					return statement;
				}
			}, generatedKeyHolder);

			if (error.isError()) {
				logger.error(context, ErrorConstants.MSG_ERROR_INSERT);
				throw new DataException(ErrorConstants.CODE_ERROR_INSERT,
						ErrorConstants.MSG_ERROR_INSERT);
			}

			if (generatedKeyHolder != null
					&& generatedKeyHolder.getKey() != null) {
				Field pkField = sqlUtil.getPKField(type);
				int generatedPk = generatedKeyHolder.getKey().intValue();
				logger.info(context, "generated PK{}", generatedPk);
				pkField.setInt(model, generatedPk);
			}
			return model;
		} catch (DataException e) {
			logger.error(context, "create operation failed", e);
			throw e;
		} catch (DataAccessException e) {
			logger.error(context, "create operation failed", e);
			throw new DataException(ErrorConstants.CODE_ERROR_INSERT,
					ErrorConstants.MSG_ERROR_INSERT, e);
		} catch (Exception e) {
			logger.error(context, "create operation failed", e);
			throw new DataException(ErrorConstants.CODE_ERROR_INSERT,
					ErrorConstants.MSG_ERROR_INSERT, e);
		}
	}

	public T getByPK(final int pk, final Class<T> type, RequestContext context)
			throws DataException {
		logger.info(context, "Get called for type {} pk{}", type.getName(), pk);
		try {
			final ErrorObject error = new ErrorObject();
			final List<ColumnDto> columns = sqlUtil.getColumns(type);
			final Field pkField = sqlUtil.getPKField(type);
			final List<CriteriaDto> criterion = new ArrayList<CriteriaDto>();
			CriteriaDto criteria = new CriteriaDto();
			criteria.setColumnName(pkField.getName());
			criteria.setSqlType(Types.INTEGER);
			criteria.setOperator(SQLOperator.EQUALS);
			criteria.setValue(pk);
			criterion.add(criteria);

			final String selectQuery = sqlUtil.selectPrepareStatement(columns,
					criterion, type);
			logger.info(context, "select query {}", selectQuery);

			T model = jdbcTemplate.query(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection con)
						throws SQLException {
					PreparedStatement statement = con
							.prepareStatement(selectQuery);
					for (int i = 0; i < criterion.size(); i++) {
						CriteriaDto criteria = criterion.get(i);
						sqlUtil.setStatementValue(i + 1, criteria.getSqlType(),
								criteria.getValue(), statement, error);
					}

					return statement;
				}
			}, new ResultSetExtractor<T>() {
				public T extractData(ResultSet rs) throws SQLException,
						DataAccessException {
					T model = null;
					try {
						if (rs.next()) {
							model = type.newInstance();
							for (int i = 0; i < columns.size(); i++) {
								ColumnDto column = columns.get(i);
								Object value = sqlUtil.getResultSetValue(i + 1,
										column.getColumnType(), rs, error);
								if (value != null)
									column.getField().set(model, value);
							}

							pkField.set(model, pk);
						}
					} catch (IllegalAccessException e) {
						error.setError(true);
						error.setException(e);
					} catch (Exception e) {
						error.setError(true);
						error.setException(e);
					}
					return model;
				}
			});

			if (error.isError()) {
				if (error.getException() != null) {
					logger.error(context, ErrorConstants.MSG_ERROR_SELECT,
							error.getException());
					throw new DataException(ErrorConstants.CODE_ERROR_SELECT,
							ErrorConstants.MSG_ERROR_SELECT,
							error.getException());
				} else {
					logger.error(context, ErrorConstants.MSG_ERROR_SELECT);
					throw new DataException(ErrorConstants.CODE_ERROR_SELECT,
							ErrorConstants.MSG_ERROR_SELECT);
				}
			}

			return model;
		} catch (DataException e) {
			logger.error(context, "select operation failed", e);
			throw e;
		} catch (DataAccessException e) {
			logger.error(context, "select operation failed", e);
			throw new DataException(ErrorConstants.CODE_ERROR_SELECT,
					ErrorConstants.MSG_ERROR_SELECT, e);
		} catch (Exception e) {
			logger.error(context, "select operation failed", e);
			throw new DataException(ErrorConstants.CODE_ERROR_SELECT,
					ErrorConstants.MSG_ERROR_SELECT, e);
		}
	}

	public List<T> getAll(final Class<T> type, RequestContext context)
			throws DataException {
		logger.info(context, "GetAll called for type {}", type.getName());
		try {
			final ErrorObject error = new ErrorObject();
			final List<ColumnDto> columns = sqlUtil.getColumns(type);

			final String selectQuery = sqlUtil.selectPrepareStatement(columns,
					null, type);
			logger.info(context, "select query {}", selectQuery);

			List<T> models = jdbcTemplate.query(selectQuery,
					new RowMapper<T>() {
						public T mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							T model = null;
							try {
								model = type.newInstance();
								for (int i = 0; i < columns.size(); i++) {
									ColumnDto column = columns.get(i);
									Object value = sqlUtil.getResultSetValue(
											i + 1, column.getColumnType(), rs,
											error);
									if (value != null)
										column.getField().set(model, value);
								}
							} catch (IllegalAccessException e) {
								error.setError(true);
								error.setException(e);
							} catch (Exception e) {
								error.setError(true);
								error.setException(e);
							}

							return model;
						}
					});

			if (error.isError()) {
				if (error.getException() != null) {
					logger.error(context, ErrorConstants.MSG_ERROR_SELECT,
							error.getException());
					throw new DataException(ErrorConstants.CODE_ERROR_SELECT,
							ErrorConstants.MSG_ERROR_SELECT,
							error.getException());
				} else {
					logger.error(context, ErrorConstants.MSG_ERROR_SELECT);
					throw new DataException(ErrorConstants.CODE_ERROR_SELECT,
							ErrorConstants.MSG_ERROR_SELECT);
				}
			}

			return models;
		} catch (DataException e) {
			logger.error(context, "select operation failed", e);
			throw e;
		} catch (DataAccessException e) {
			logger.error(context, "select operation failed", e);
			throw new DataException(ErrorConstants.CODE_ERROR_SELECT,
					ErrorConstants.MSG_ERROR_SELECT, e);
		} catch (Exception e) {
			logger.error(context, "select operation failed", e);
			throw new DataException(ErrorConstants.CODE_ERROR_SELECT,
					ErrorConstants.MSG_ERROR_SELECT, e);
		}
	}

	public List<T> get(final Class<T> type, T model, RequestContext context)
			throws DataException {
		logger.info(context, "Get called for type {} with criteria {}",
				type.getName(), model);
		try {
			final ErrorObject error = new ErrorObject();
			final List<ColumnDto> columns = sqlUtil.getColumns(type);
			final List<CriteriaDto> criterion = sqlUtil.getCriterion(model,
					type);

			final String selectQuery = sqlUtil.selectPrepareStatement(columns,
					criterion, type);
			logger.info(context, "select query {}", selectQuery);

			List<T> models = jdbcTemplate.query(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection con)
						throws SQLException {
					PreparedStatement statement = con
							.prepareStatement(selectQuery);
					for (int i = 0; i < criterion.size(); i++) {
						CriteriaDto criteria = criterion.get(i);
						sqlUtil.setStatementValue(i + 1, criteria.getSqlType(),
								criteria.getValue(), statement, error);
					}

					return statement;
				}
			}, new RowMapper<T>() {
				public T mapRow(ResultSet rs, int rowNum) throws SQLException {
					T model = null;
					try {
						model = type.newInstance();
						for (int i = 0; i < columns.size(); i++) {
							ColumnDto column = columns.get(i);
							Object value = sqlUtil.getResultSetValue(i + 1,
									column.getColumnType(), rs, error);
							if (value != null)
								column.getField().set(model, value);
						}
					} catch (IllegalAccessException e) {
						error.setError(true);
						error.setException(e);
					} catch (Exception e) {
						error.setError(true);
						error.setException(e);
					}

					return model;
				}
			});

			if (error.isError()) {
				if (error.getException() != null) {
					logger.error(context, ErrorConstants.MSG_ERROR_SELECT,
							error.getException());
					throw new DataException(ErrorConstants.CODE_ERROR_SELECT,
							ErrorConstants.MSG_ERROR_SELECT,
							error.getException());
				} else {
					logger.error(context, ErrorConstants.MSG_ERROR_SELECT);
					throw new DataException(ErrorConstants.CODE_ERROR_SELECT,
							ErrorConstants.MSG_ERROR_SELECT);
				}
			}

			return models;
		} catch (DataException e) {
			logger.error(context, "select operation failed", e);
			throw e;
		} catch (DataAccessException e) {
			logger.error(context, "select operation failed", e);
			throw new DataException(ErrorConstants.CODE_ERROR_SELECT,
					ErrorConstants.MSG_ERROR_SELECT, e);
		} catch (Exception e) {
			logger.error(context, "select operation failed", e);
			throw new DataException(ErrorConstants.CODE_ERROR_SELECT,
					ErrorConstants.MSG_ERROR_SELECT, e);
		}
	}

	public T updateByPK(int pk, T model, Class<T> type, RequestContext context)
			throws DataException {
		logger.info(context, "Update called for type {} pk {} values{}",
				type.getName(), pk, model);
		try {
			final ErrorObject error = new ErrorObject();
			final List<ColumnDto> columns = sqlUtil.getColumnData(type, model);
			final Field pkField = sqlUtil.getPKField(type);
			final List<CriteriaDto> criterion = new ArrayList<CriteriaDto>();
			final CriteriaDto criteria = new CriteriaDto();
			criteria.setColumnName(pkField.getName());
			criteria.setSqlType(Types.INTEGER);
			criteria.setOperator(SQLOperator.EQUALS);
			criteria.setValue(pk);
			criterion.add(criteria);

			final String updateQuery = sqlUtil.updatePrepareStatement(columns,
					criterion, type);

			logger.info(context, "update query {}", updateQuery);

			jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement statement = conn
							.prepareStatement(updateQuery);
					int i = 0;
					for (; i < columns.size(); i++) {
						ColumnDto column = columns.get(i);
						Object value = column.getColumnValue();
						int sqlType = column.getColumnType();
						sqlUtil.setStatementValue(i + 1, sqlType, value,
								statement, error);
					}

					// set the pk column
					sqlUtil.setStatementValue(i + 1, criteria.getSqlType(),
							criteria.getValue(), statement, error);
					return statement;
				}
			});

			if (error.isError()) {
				logger.error(context, ErrorConstants.MSG_ERROR_UPDATE);
				throw new DataException(ErrorConstants.CODE_ERROR_UPDATE,
						ErrorConstants.MSG_ERROR_UPDATE);
			}

			return this.getByPK(pk, type, context);
		} catch (DataException e) {
			logger.error(context, "update operation failed", e);
			throw e;
		} catch (DataAccessException e) {
			logger.error(context, "update operation failed", e);
			throw new DataException(ErrorConstants.CODE_ERROR_UPDATE,
					ErrorConstants.MSG_ERROR_UPDATE, e);
		} catch (Exception e) {
			logger.error(context, "update operation failed", e);
			throw new DataException(ErrorConstants.CODE_ERROR_UPDATE,
					ErrorConstants.MSG_ERROR_UPDATE, e);
		}
	}

	public int deleteByPK(int pk, Class<T> type, RequestContext context)
			throws DataException {
		logger.info(context, "delete called for type {} pk {}",
				type.getName(), pk);
		try {
			final ErrorObject error = new ErrorObject();

			final Field pkField = sqlUtil.getPKField(type);
			final List<CriteriaDto> criterion = new ArrayList<CriteriaDto>();
			final CriteriaDto criteria = new CriteriaDto();
			criteria.setColumnName(pkField.getName());
			criteria.setSqlType(Types.INTEGER);
			criteria.setOperator(SQLOperator.EQUALS);
			criteria.setValue(pk);
			criterion.add(criteria);

			final String deleteQuery = sqlUtil.deletePrepareStatement(criterion, type);

			logger.info(context, "delete query {}", deleteQuery);

			int rowsDeleted = jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement statement = conn
							.prepareStatement(deleteQuery);
					
					// set the pk column
					sqlUtil.setStatementValue(1, criteria.getSqlType(),
							criteria.getValue(), statement, error);
					return statement;
				}
			});

			if (error.isError()) {
				logger.error(context, ErrorConstants.MSG_ERROR_DELETE);
				throw new DataException(ErrorConstants.CODE_ERROR_DELETE,
						ErrorConstants.MSG_ERROR_DELETE);
			}

			return rowsDeleted;
		} catch (DataException e) {
			logger.error(context, "delete operation failed", e);
			throw e;
		} catch (DataAccessException e) {
			logger.error(context, "delete operation failed", e);
			throw new DataException(ErrorConstants.CODE_ERROR_DELETE,
					ErrorConstants.MSG_ERROR_DELETE, e);
		} catch (Exception e) {
			logger.error(context, "delete operation failed", e);
			throw new DataException(ErrorConstants.CODE_ERROR_DELETE,
					ErrorConstants.MSG_ERROR_DELETE, e);
		}
	}
}
