package com.excilys.cdb.exception;

public class DatabaseConnectionException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DatabaseConnectionException(String message) {
		super(message);
	}

}
