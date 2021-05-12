package com.excilys.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	private static final String urlDatabase = "jdbc:mysql://127.0.0.1/computer-database-db?serverTimezone=UTC";
	private static final String user = "admincdb";
	private static final String password = "qwerty1234";
	
	private Connection con;
	private static Database instance = null;
	
	private Database() {
		// nothing to initialize
	}
	
	public static Database getInstance() {
		if (instance == null)
			instance = new Database();
		return instance;
	}

	/**
	 * Connect to the database
	 */
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

	/**
	 * Close connection with the database
	 */
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
