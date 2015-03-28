/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Nov 27, 2014 1:49:09 PM 2014
 *
 *********************************************************/
package com.se.common.data.model;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.se.common.annotation.DataColumn;
import com.se.common.util.JsonUtil;

/**
 * Generic Entity representing the database table. All the data models must
 * extend this generic model.
 * 
 * Guidelines for Writing Model classes: 1) The classes must have a
 * {@link Table} annotation. 2) The {@link Table} annotation must have name()
 * attribute set. 3) All the fields in the class must have PUBLIC identifier. 4)
 * All the fields must be annotated with {@link DataColumn} 5) The name of field
 * must be exactly same as the database table column name.
 * 
 * @author Saurabh Tiwari
 *
 */
@XmlRootElement
// Only the fields will be included in the JSON
@XmlAccessorType(XmlAccessType.FIELD)
// Ignore any unknown properties in the json while unmarshalling
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericDataModel implements Serializable {

	private static final long serialVersionUID = 00000000000000001L;

	/**
	 * This method must be overridden by all the extending classes.
	 * @param json
	 * @return
	 */
	public static GenericDataModel valueOf(String json){
		return JsonUtil.fromJson(json, GenericDataModel.class);
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

}
