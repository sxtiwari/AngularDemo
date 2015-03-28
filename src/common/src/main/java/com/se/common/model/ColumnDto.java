/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Dec 7, 2014
 *
 *********************************************************/
package com.se.common.model;

import java.lang.reflect.Field;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Saurabh Tiwari
 *
 */
public class ColumnDto {
	private int columnType;
	private String columnName;
	private Object columnValue;
	private Field field;

	public int getColumnType() {
		return columnType;
	}

	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Object getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(Object columnValue) {
		this.columnValue = columnValue;
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

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

}
