package com.excilys.exception;

public class ComputerDateGreaterLessThan1970 extends RuntimeException {
	
	public ComputerDateGreaterLessThan1970() {
		super();
	}
	
	public ComputerDateGreaterLessThan1970(String message) {
		super(message);
	}
	
	public ComputerDateGreaterLessThan1970(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ComputerDateGreaterLessThan1970(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public ComputerDateGreaterLessThan1970(Throwable cause) {
		super(cause);
	}
}
