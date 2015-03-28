/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Dec 18, 2014
 *
 *********************************************************/
package com.se.common.enumeration;

/**
 * @author Saurabh Tiwari
 *
 */
public enum SQLOperator {

	LESSTHAN("<"), GREATERTHAN(">"), EQUALS("="), NOTEQUALS("!="), LESSTHANEQUALS("<="), GREATHERTHANEQUALS(">="), LIKE(
		      "like"), CONTAINS("contains");
	
	private String operator;
	
	SQLOperator(String operator){
		this.operator = operator;
	}
	
	public String getValue(){
		return this.operator;
	}
}
