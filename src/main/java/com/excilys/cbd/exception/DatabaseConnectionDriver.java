package com.excilys.cbd.exception;

public class DatabaseConnectionDriver extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DatabaseConnectionDriver(String message) {
		super(message);
	}
}
