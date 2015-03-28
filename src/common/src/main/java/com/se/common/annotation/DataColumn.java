/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Dec 6, 2014
 *
 *********************************************************/
package com.se.common.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.persistence.Column;

import com.se.common.dao.GenericDaoImpl;

/**
 * DataColumn annotation which basically extends the 
 * {@link Column} annotation, by adding
 * another property sqlType. This is used in
 * {@link GenericDaoImpl} to perform CRUD operations.
 * @author Saurabh Tiwari
 *
 */

@Target({METHOD, FIELD}) 
@Retention(RUNTIME)
public @interface DataColumn{
	public Column column();
	public int sqlType();
}
