package com.excilys.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseImpl implements IDatabase {
	private static final String urlDatabase = "jdbc:mysql://127.0.0.1/computer-database-db?serverTimezone=UTC";
	private static final String user = "admincdb";
	private static final String password = "qwerty1234";
	
	private Connection con;
	private static DatabaseImpl instance = null;
	
	private DatabaseImpl() {
		// nothing to initialize
	}
	
	public static DatabaseImpl getInstance() {
		if (instance == null)
			instance = new DatabaseImpl();
		return instance;
	}

	@Override
	public void connection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			System.err.print("Driver not found");
			System.exit(1);
		}
		try {
			con = DriverManager.getConnection(urlDatabase, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		try {
			con.close();
		} catch(SQLException e) {
			// do nothing, desperate case
		}
	}
	
	/// setters & getters
	public Connection getConnection() {
		return con;
	}
}
