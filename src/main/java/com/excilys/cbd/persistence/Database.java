package com.excilys.cbd.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.excilys.cbd.exception.DatabaseConnectionException;

public class Database implements AutoCloseable {
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
	 * @return connection object
	 */
	public Connection connection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print("Driver not found");
		}
		try {
			con = DriverManager.getConnection(urlDatabase, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseConnectionException("Connection impossible to database");
		}
		return con;
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
