/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Dec 6, 2014
 *
 *********************************************************/
package com.se.common.util;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.se.common.annotation.DataColumn;
import com.se.common.constant.ErrorConstants;
import com.se.common.constant.SQLConstants;
import com.se.common.data.model.GenericDataModel;
import com.se.common.enumeration.SQLOperator;
import com.se.common.exception.DataException;
import com.se.common.exception.ErrorObject;
import com.se.common.model.ColumnDto;
import com.se.common.model.CriteriaDto;

/**
 * The SQL utility class to create the SQL queries from Model class. Also, acts
 * as helper class to perform certain operations. Refer individual methods for
 * more details.
 * 
 * @author Saurabh Tiwari
 *
 */

@Component
public class SqlUtil {

	public <T extends GenericDataModel> List<ColumnDto> getColumnData(
			Class<T> type, T model) throws DataException {

		List<ColumnDto> columns = new ArrayList<ColumnDto>();
		try {
			Table table = type.getAnnotation(Table.class);
			Field[] fields = type.getDeclaredFields();

			if (table != null && fields != null) {
				for (Field field : fields) {
					field.setAccessible(true);
					DataColumn column = field.getAnnotation(DataColumn.class);
					if (column != null) {
						String columnName = field.getName();
						int sqlType = column.sqlType();
						Object value = field.get(model);
						if (value != null) {
							ColumnDto data = new ColumnDto();
							data.setColumnName(columnName);
							data.setColumnType(sqlType);
							data.setColumnValue(value);
							data.setField(field);
							columns.add(data);
						}
					}
				}
			}
		} catch (IllegalAccessException e) {
			throw new DataException(ErrorConstants.CODE_ERROR_BUILDING_QUERY,
					ErrorConstants.MSG_ERROR_BUILDING_QUERY, e);
		} catch (Exception e) {
			throw new DataException(ErrorConstants.CODE_ERROR_BUILDING_QUERY,
					ErrorConstants.MSG_ERROR_BUILDING_QUERY, e);
		}

		return columns;
	}

	public <T extends GenericDataModel> List<ColumnDto> getColumns(Class<T> type)
			throws DataException {

		List<ColumnDto> columns = new ArrayList<ColumnDto>();
		try {
			Table table = type.getAnnotation(Table.class);
			Field[] fields = type.getDeclaredFields();

			if (table != null && fields != null) {
				for (Field field : fields) {
					field.setAccessible(true);
					DataColumn column = field.getAnnotation(DataColumn.class);
					if (column != null) {
						String columnName = field.getName();
						int sqlType = column.sqlType();
						ColumnDto data = new ColumnDto();
						data.setColumnName(columnName);
						data.setColumnType(sqlType);
						data.setField(field);
						columns.add(data);
					}
				}

				ColumnDto pkColumn = this.getPKColumn(type, null);
				if (pkColumn != null)
					columns.add(pkColumn);
			}
		} catch (Exception e) {
			throw new DataException(ErrorConstants.CODE_ERROR_BUILDING_QUERY,
					ErrorConstants.MSG_ERROR_BUILDING_QUERY, e);
		}

		return columns;
	}

	public <T extends GenericDataModel> Field getPKField(Class<T> type) {
		Field[] fields = type.getDeclaredFields();
		for (Field field : fields) {
			Id id = field.getAnnotation(Id.class);
			if (id != null) {
				field.setAccessible(true);
				return field;
			}
		}

		return null;
	}

	private <T extends GenericDataModel> ColumnDto getPKColumn(Class<T> type,
			T model) throws DataException {
		try {
			Field[] fields = type.getDeclaredFields();
			for (Field field : fields) {
				Id id = field.getAnnotation(Id.class);
				if (id != null) {
					field.setAccessible(true);
					ColumnDto column = new ColumnDto();
					column.setColumnName(field.getName());
					column.setColumnType(Types.INTEGER);
					column.setField(field);
					if (model != null)
						column.setColumnValue(field.get(model));
					return column;
				}
			}
		} catch (IllegalAccessException e) {
			throw new DataException(ErrorConstants.CODE_ERROR_BUILDING_QUERY,
					ErrorConstants.MSG_ERROR_BUILDING_QUERY, e);
		} catch (Exception e) {
			throw new DataException(ErrorConstants.CODE_ERROR_BUILDING_QUERY,
					ErrorConstants.MSG_ERROR_BUILDING_QUERY, e);
		}

		return null;
	}

	public <T extends GenericDataModel> String insertPrepareStatement(
			List<ColumnDto> columns, Class<T> type) {
		String tableName = "";
		String columnNames = "";
		String placeHolders = "";

		Table table = type.getAnnotation(Table.class);
		tableName = table.name();
		for (ColumnDto column : columns) {
			columnNames += "," + (column.getColumnName());
			placeHolders += ",?";
		}

		String insertSql = SQLConstants.INSERT;
		insertSql = insertSql + tableName;
		insertSql = insertSql + "(" + columnNames.substring(1) + ")";
		insertSql = insertSql + " values";
		insertSql = insertSql + "(" + placeHolders.substring(1) + ")";

		return insertSql;
	}

	public <T extends GenericDataModel> String selectPrepareStatement(
			List<ColumnDto> columns, List<CriteriaDto> criterion, Class<T> type) {
		String columnNames = "";
		String selectSql = SQLConstants.SELECT;
		Table table = type.getAnnotation(Table.class);
		String tableName = table.name();
		for (ColumnDto column : columns) {
			columnNames += "," + tableName + "." + column.getColumnName()
					+ " AS " + column.getColumnName();
		}

		selectSql = selectSql + columnNames.substring(1);
		selectSql = selectSql + SQLConstants.FROM + tableName;

		String whereClause = "";
		if (criterion != null && criterion.size() > 0) {
			for (CriteriaDto criteria : criterion) {
				String columnName = criteria.getColumnName();
				if (tableName != null)
					columnName = tableName + "." + columnName;

				whereClause += SQLConstants.AND + columnName
						+ criteria.getOperator().getValue() + "?";
			}
			selectSql += SQLConstants.WHERE + whereClause.substring(4);
		}

		return selectSql;
	}

	public <T extends GenericDataModel> String updatePrepareStatement(
			List<ColumnDto> columns, List<CriteriaDto> criterion, Class<T> type) {
		String columnNames = "";

		Table table = type.getAnnotation(Table.class);
		String tableName = table.name();
		for (ColumnDto column : columns) {
			columnNames += "," + (column.getColumnName()) + "=?";
		}

		String updateSql = SQLConstants.UPDATE;
		updateSql = updateSql + tableName;
		updateSql = updateSql + SQLConstants.SET;
		updateSql = updateSql + columnNames.substring(1);

		String whereClause = "";
		if (criterion != null && criterion.size() > 0) {
			for (CriteriaDto criteria : criterion) {
				String columnName = criteria.getColumnName();
				if (tableName != null)
					columnName = tableName + "." + columnName;

				whereClause += SQLConstants.AND + columnName
						+ criteria.getOperator().getValue() + "?";
			}
			updateSql += SQLConstants.WHERE + whereClause.substring(4);
		}

		return updateSql;
	}

	public <T extends GenericDataModel> String deletePrepareStatement(
			List<CriteriaDto> criterion, Class<T> type) {
		Table table = type.getAnnotation(Table.class);
		String tableName = table.name();

		String deleteSql = SQLConstants.DELETE;
		deleteSql = deleteSql + tableName;
		
		String whereClause = "";
		if (criterion != null && criterion.size() > 0) {
			for (CriteriaDto criteria : criterion) {
				String columnName = criteria.getColumnName();
				if (tableName != null)
					columnName = tableName + "." + columnName;

				whereClause += SQLConstants.AND + columnName
						+ criteria.getOperator().getValue() + "?";
			}
			
			deleteSql += SQLConstants.WHERE + whereClause.substring(4);
		}

		return deleteSql;
	}

	public <T extends GenericDataModel> List<CriteriaDto> getCriterion(T model,
			Class<T> type) throws DataException {
		Table table = type.getAnnotation(Table.class);
		List<ColumnDto> columns = this.getColumnData(type, model);
		ColumnDto pkColumn = this.getPKColumn(type, model);
		if ((Integer) pkColumn.getColumnValue() != 0)
			columns.add(pkColumn);

		List<CriteriaDto> criterion = new ArrayList<CriteriaDto>();
		for (ColumnDto column : columns) {
			CriteriaDto criteria = new CriteriaDto();
			criteria.setColumnName(column.getColumnName());
			criteria.setOperator(SQLOperator.EQUALS);
			criteria.setSqlType(column.getColumnType());
			criteria.setValue(column.getColumnValue());
			criteria.setTableName(table.name());

			criterion.add(criteria);
		}

		return criterion;
	}

	public void setStatementValue(int index, int sqlType, Object value,
			PreparedStatement statement, ErrorObject error) throws SQLException {
		switch (sqlType) {
		case 0:
			error.setError(true);
			break;
		case Types.VARCHAR:
			statement.setString(index, (String) value);
			break;
		case Types.DOUBLE:
			statement.setDouble(index, (Double) value);
			break;
		case Types.INTEGER:
			statement.setInt(index, (Integer) value);
			break;
		case Types.FLOAT:
			statement.setFloat(index, (Float) value);
			break;
		case Types.TINYINT:
			statement.setShort(index, (Short) value);
			break;
		case Types.BIGINT:
			statement.setLong(index, (Long) value);
			break;
		case Types.BIT:
			statement.setBoolean(index, (Boolean) value);
			break;
		case Types.DATE:
			statement.setDate(index,
					new Date(((java.util.Date) value).getTime()));
			break;
		case Types.TIMESTAMP:
			statement.setTimestamp(index, new Timestamp(
					((java.util.Date) value).getTime()));
			break;
		}
	}

	public Object getResultSetValue(int index, int sqlType, ResultSet rs,
			ErrorObject error) throws SQLException {
		switch (sqlType) {
		case 0:
			error.setError(true);
			break;
		case Types.VARCHAR:
			return rs.getString(index);
		case Types.DOUBLE:
			return rs.getDouble(index);
		case Types.INTEGER:
			return rs.getInt(index);
		case Types.FLOAT:
			return rs.getFloat(index);
		case Types.TINYINT:
			return rs.getShort(index);
		case Types.BIGINT:
			return rs.getLong(index);
		case Types.BIT:
			return rs.getBoolean(index);
		case Types.DATE:
			if (rs.getDate(index) != null)
				return new java.util.Date(rs.getDate(index).getTime());
			return null;
		case Types.TIMESTAMP:
			if (rs.getTimestamp(index) != null)
				return new java.util.Date(rs.getTimestamp(index).getTime());
			return null;
		}

		return null;
	}
}
