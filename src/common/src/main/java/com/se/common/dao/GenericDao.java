/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Dec 7, 2014
 *
 *********************************************************/
package com.se.common.dao;

import java.util.List;

import com.se.common.data.model.GenericDataModel;
import com.se.common.exception.DataException;
import com.se.common.util.RequestContext;

/**
 * @author Saurabh Tiwari
 *
 */
public interface GenericDao<T extends GenericDataModel> {

	public T create(T model, Class<T> type, RequestContext context) throws DataException;
	public T getByPK(int pk, final Class<T> type, RequestContext context) throws DataException;
	public List<T> getAll(final Class<T> type, RequestContext context) throws DataException;
	public List<T> get(final Class<T> type, T model, RequestContext context) throws DataException;
	public T updateByPK(int pk, T model, Class<T> type, RequestContext context) throws DataException;
	public int deleteByPK(int pk, Class<T> type, RequestContext context) throws DataException;
}
