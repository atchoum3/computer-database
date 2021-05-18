package com.excilys.cbd.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cbd.exception.DatabaseConnectionDriver;
import com.excilys.cbd.exception.DatabaseConnectionException;

public class Database implements AutoCloseable {
	private static final String urlDatabase = "jdbc:mysql://127.0.0.1/computer-database-db?serverTimezone=UTC";
	private static final String user = "admincdb";
	private static final String password = "qwerty1234E";
	
	private static Logger logger = LoggerFactory.getLogger(Database.class);
	
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
			logger.error(e.getMessage(), e);
			throw new DatabaseConnectionDriver("Driver not found");
		}
		try {
			con = DriverManager.getConnection(urlDatabase, user, password);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
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
			logger.error(e.getMessage(), e);
		}
	}
	
	/// setters & getters
	public Connection getConnection() {
		return con;
	}
}
