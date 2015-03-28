/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Nov 27, 2014 1:25:35 PM 2014
 *
 *********************************************************/

package com.se.common.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * 
 * @author Saurabh Tiwari
 *
 */

public class JsonUtil {

	public static <T> String toJson(T object, Class<T> type){
		Gson gson = new GsonBuilder().create();
		return gson.toJson(object, type);
	}
	
	public static <T> T fromJson(String json, Class<T> type){
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(json, type);
	}
	
	public static <T> String toJson(T object, Type type){
		Gson gson = new GsonBuilder().create();
		return gson.toJson(object, type);
	}
}
