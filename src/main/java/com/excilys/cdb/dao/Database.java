package com.excilys.cdb.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.CustomSQLException;

public class Database extends BasicDataSource {
	private static Database instance;
	private static Logger logger = LoggerFactory.getLogger(Database.class);
	
	private static final String FILE_NAME_CONFIGURATION = "db.properties";
	private static final String PROPERTY_DRIVER_CLASS = "db.driverClassName";
	private static final String PROPERTY_URL = "db.url";
	private static final String PROPERTY_USER = "db.username";
	private static final String PROPERTY_PASSWORD = "db.password";
	
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
		super();
		Properties properties = readProperties();
		this.setDriverClassName(properties.getProperty(PROPERTY_DRIVER_CLASS));
		this.setUrl(properties.getProperty(PROPERTY_URL));
		this.setUsername(properties.getProperty(PROPERTY_USER));
		this.setPassword(properties.getProperty(PROPERTY_PASSWORD));
		logger.debug("driverClass=" + getDriverClassName() + " url=" + getUrl() + " user=" + getUsername() + " password=" + getPassword());
	}
	
	public static Database getInstance() throws CustomSQLException {
		if (instance == null) {
			instance = new Database();
		}
		return instance;
	}

	
	@Override
	public void close() {
		try {
			super.close();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
