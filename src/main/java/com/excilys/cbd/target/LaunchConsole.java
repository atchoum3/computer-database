package com.excilys.cbd.target;

import com.excilys.cbd.controler.ConsoleControler;
import com.excilys.cbd.exception.DatabaseConnectionException;

public class LaunchConsole {
	public static void main(String[] args) {
		try {
			ConsoleControler.getInstance().start();
		} catch(DatabaseConnectionException e) {
			System.err.println(e.getMessage());
		}
	}
}
