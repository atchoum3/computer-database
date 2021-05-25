package com.excilys.cdb.target;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.controler.cli.ConsoleControler;
import com.excilys.cdb.exception.DatabaseConnectionException;

public class LaunchConsole {
	private static Logger logger = LoggerFactory.getLogger(LaunchConsole.class);

	public static void main(String[] args) {
		try {
			ConsoleControler.getInstance().start();
		} catch (DatabaseConnectionException e) {
			System.err.println(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
