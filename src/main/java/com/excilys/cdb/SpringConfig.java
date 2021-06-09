package com.excilys.cdb;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
@ComponentScan(basePackages =  {
		"com.excilys.cdb.controller.cli",
		"com.excilys.cdb.controller.web",
		"com.excilys.cdb.service",
		"com.excilys.cdb.ui",
		"com.excilys.cdb.bindingFront.mapper",
		"com.excilys.cdb.bindingFront.validator",
		"com.excilys.cdb.bindingBack.mapper",
		"com.excilys.cdb.dao",
		"com.excilys.cdb.dao.mapper",
		})
public class SpringConfig {
	private static Logger logger = LoggerFactory.getLogger(SpringConfig.class);
	private static final String FILE_NAME_CONF_DB = "db.properties";


	@Bean
	public DataSource dataSource() {
		Properties propperties = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(FILE_NAME_CONF_DB);
		try {
			propperties.load(inputStream);

			HikariConfig config = new HikariConfig(propperties);
			return new HikariDataSource(config);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}
