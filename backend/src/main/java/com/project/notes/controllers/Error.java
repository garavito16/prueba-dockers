package com.project.notes.controllers;

import java.util.ArrayList;
import java.util.List;

public class Error {
	int code;
	String message;
	List<String> cause;

	public int getCode() {
	    return code;
	}

	public void setCode(int code) {
	    this.code = code;
	}

	public String getMessage() {
	    return message;
	}

	public void setMessage(String message) {
	    this.message = message;
	}

	public List<String> getCause() {
		return cause;
	}

	public void setCause(List<String> cause) {
		this.cause = cause;
	}

	public void addCause(String cause) {
		if(this.cause == null) {
			this.cause = new ArrayList<String>();
		}
		this.cause.add(cause);
	}
}
