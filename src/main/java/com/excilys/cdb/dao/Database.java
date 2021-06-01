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
	private static final String PROPERTY_DRIVER_CLASS = "db.driverClassName";
	private static final String PROPERTY_URL = "db.url";
	private static final String PROPERTY_USER = "db.username";
	private static final String PROPERTY_PASSWORD = "db.password";
	private static final String PROPERTY_CACHE_PREP_STMTS = "db.prop.cachePrepStmts";
	private static final String PROPERTY_PREP_STMTS_CACHE_SIZE = "db.prop.prepStmtCacheSize";
	private static final String PROPERTY_PREP_STMTS_CACHE_SQL_LIMIT = "db.prop.prepStmtCacheSqlLimit";
	private static final String PROPERTY_PREP_MAX_LIFE_TIME = "db.prop.maxLifetime";
	
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
		Properties properties = readProperties();
		config.setDriverClassName(properties.getProperty(PROPERTY_DRIVER_CLASS));
		config.setJdbcUrl(properties.getProperty(PROPERTY_URL));
		config.setUsername(properties.getProperty(PROPERTY_USER));
		config.setPassword(properties.getProperty(PROPERTY_PASSWORD));
		
		config.addDataSourceProperty("cachePrepStmts", properties.getProperty(PROPERTY_CACHE_PREP_STMTS));
        config.addDataSourceProperty("prepStmtCacheSize", properties.getProperty(PROPERTY_PREP_STMTS_CACHE_SIZE));
        config.addDataSourceProperty("prepStmtCacheSqlLimit", properties.getProperty(PROPERTY_PREP_STMTS_CACHE_SQL_LIMIT));
        config.addDataSourceProperty("maxLifetime", properties.getProperty(PROPERTY_PREP_MAX_LIFE_TIME));
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
