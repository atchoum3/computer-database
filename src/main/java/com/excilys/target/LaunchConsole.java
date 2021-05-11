package com.excilys.target;

import java.sql.SQLException;

import com.excilys.model.Computer;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.Database;
import com.excilys.ui.Console;


public class LaunchConsole {
	public static void main(String[] args) throws SQLException {
		Database.getInstance().connection();
		
		Console console = new Console();
		console.start();
	}
}
