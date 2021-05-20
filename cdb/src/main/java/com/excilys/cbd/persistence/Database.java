package com.excilys.cbd.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Database extends BasicDataSource implements AutoCloseable {
	private static Database instance;
	private static Logger logger = LoggerFactory.getLogger(Database.class);
	
	private static final String FILE_NAME_CONFIGURATION = "db.properties";
	private static final String PROPERTY_DRIVER_CLASS = "db.driverClassName";
	private static final String PROPERTY_URL = "db.url";
	private static final String PROPERTY_USER = "db.username";
	private static final String PROPERTY_PASSWORD = "db.password";
	
	private  Properties readProperties() {
		Properties properties = new Properties();
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		// classLoader.getResourceAsStream(file) search the file and open the file
		try (InputStream configFile = classLoader.getResourceAsStream(FILE_NAME_CONFIGURATION)) {
			properties.load(configFile);
        } catch (IOException e) {
        	logger.error(e.getMessage(), e);
        	//TODO
        	//throw new FileNotFoundException("configuration file " + FILE_NAME_CONFIGURATION + "has not been found.");
        }
		return properties;
	}
	
	private Database() {
		super();
		Properties properties = readProperties();
		this.setDriverClassName(properties.getProperty(PROPERTY_DRIVER_CLASS));
		this.setUrl(properties.getProperty(PROPERTY_URL));
		this.setUsername(properties.getProperty(PROPERTY_USER));
		this.setPassword(properties.getProperty(PROPERTY_PASSWORD));
		logger.debug("driverClass=" + getDriverClassName() + " url=" + getUrl() + " user=" + getUsername() + " password=" + getPassword());
	}
	
	public static Database getInstance() {
		if (instance == null) {
			instance = new Database();
		}
		return instance;
	}

	/**
	 * Close connection with the database.
	 */
	public void close() {
		try {
			getConnection().close();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
