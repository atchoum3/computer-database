package com.excilys.cdb.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.CustomSQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Database implements AutoCloseable {
	private static Database instance;
	private static Logger logger = LoggerFactory.getLogger(Database.class);

	private static final String FILE_NAME_CONFIGURATION = "db.properties";

	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource ds;

	private  Properties readProperties() throws CustomSQLException {
		Properties properties = new Properties();

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		// classLoader.getResourceAsStream(file) search the file and open the file
		try (InputStream configFile = classLoader.getResourceAsStream(FILE_NAME_CONFIGURATION)) {
			properties.load(configFile);
        } catch (IOException e) {
        	logger.error(e.getMessage(), e);
        	throw new CustomSQLException("Error to connect to the database.");
        }
		return properties;
	}

	private Database() throws CustomSQLException {
		config = new HikariConfig(readProperties());
		ds = new HikariDataSource(config);
	}


	public static Database getInstance() throws CustomSQLException {
		if (instance == null) {
			instance = new Database();
		}
		return instance;
	}

	public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

	public void close() {
		ds.close();
	}
}
