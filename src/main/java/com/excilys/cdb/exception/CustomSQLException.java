package com.excilys.cdb.exception;

public class CustomSQLException extends Exception {
	private static final long serialVersionUID = 1L;

	public CustomSQLException(String message) {
		super(message);
	}
	
	public CustomSQLException(Throwable throwable) {
		super(throwable);
	}
}
