/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Nov 27, 2014 1:25:35 PM 2014
 *
 *********************************************************/

package com.se.common.data.model;

import java.sql.Types;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.se.common.annotation.DataColumn;
import com.se.common.util.JsonUtil;

/**
 * DataModel for user table.
 * 
 * @author Saurabh Tiwari
 *
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "user")
public class UserDataModel extends GenericDataModel {

	private static final long serialVersionUID = 00000000000000002L;

	@Id
	@GeneratedValue
	public int id;

	@DataColumn(column = @Column(unique = true, nullable = false), sqlType = Types.VARCHAR)
	public String email;

	@DataColumn(column = @Column(nullable = false), sqlType = Types.VARCHAR)
	public String password;

	@DataColumn(column = @Column(insertable = false, updatable = false), sqlType = Types.TIMESTAMP)
	public Date createdDate;

	@DataColumn(column = @Column(insertable = false, updatable = false), sqlType = Types.TIMESTAMP)
	public Date lastModifiedDate;
	
	@DataColumn(column = @Column(insertable = false, updatable = false), sqlType = Types.BIT)
	public boolean isGuest;

	/**
	 * 
	 */
	public UserDataModel() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * This method is needed to be able to parse the json as query param.
	 * @param json
	 * @return
	 */
	public static UserDataModel valueOf(String json){
		return JsonUtil.fromJson(json, UserDataModel.class);
	}

	public boolean isGuest() {
		return isGuest;
	}

	public void setGuest(boolean isGuest) {
		this.isGuest = isGuest;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}
