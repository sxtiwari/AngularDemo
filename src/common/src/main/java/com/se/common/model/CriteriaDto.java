/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Dec 18, 2014
 *
 *********************************************************/
package com.se.common.model;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.se.common.enumeration.SQLOperator;

/**
 * @author Saurabh Tiwari
 *
 */
public class CriteriaDto {

	private String columnName;
	private SQLOperator operator;
	private Object value;
	private String tableName;
	private int sqlType;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public SQLOperator getOperator() {
		return operator;
	}

	public void setOperator(SQLOperator operator) {
		this.operator = operator;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	public int getSqlType() {
		return sqlType;
	}

	public void setSqlType(int sqlType) {
		this.sqlType = sqlType;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
