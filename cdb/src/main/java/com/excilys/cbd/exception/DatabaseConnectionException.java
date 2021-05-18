package com.excilys.cbd.exception;

public class DatabaseConnectionException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DatabaseConnectionException(String message) {
		super(message);
	}

}
