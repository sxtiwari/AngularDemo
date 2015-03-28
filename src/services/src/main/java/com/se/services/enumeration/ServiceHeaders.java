package com.se.services.enumeration;

public enum ServiceHeaders {

	REQUEST_TOKEN_HEADER("X-RQ-Token");

	public String name;

	private ServiceHeaders(String headerName) {
		this.name = headerName;
	}

	public String getName() {
		return name;
	}

	public void setName(String headerName) {
		this.name = headerName;
	}

}
