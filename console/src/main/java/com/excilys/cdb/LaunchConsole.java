package com.excilys.cdb;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import com.excilys.cdb.controller.cli.ConsoleControler;

@Configuration
@ComponentScan(basePackages =  {
		"com.excilys.cdb.controller.cli",
		"com.excilys.cdb.service",
		"com.excilys.cdb.ui",
		"com.excilys.cdb.bindingBack.mapper",
		"com.excilys.cdb.dao",
		"com.excilys.cdb.dao.mapper",
})
public class LaunchConsole {
	private static final String FILE_NAME_CONF_DB = "db.properties";
	private static Logger logger = LoggerFactory.getLogger(LaunchConsole.class);
	private static ApplicationContext context;

	public static void main(String[] args) {
		try {
			context = new AnnotationConfigApplicationContext(LaunchConsole.class);
			ConsoleControler console = context.getBean(ConsoleControler.class);
			console.start();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

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
	
	@Bean
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(dataSource());
	}
}
