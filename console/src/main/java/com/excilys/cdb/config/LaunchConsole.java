package com.excilys.cdb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.excilys.cdb.controller.cli.ConsoleControler;

import config.PersistenceConfig;

@Configuration
@Import({PersistenceConfig.class})
@ComponentScan(basePackages =  {
		"com.excilys.cdb.binding.persistence.mapper",
		"com.excilys.cdb.binding.web.mapper",
		"com.excilys.cdb.binding.web.validator",
		"com.excilys.cdb.controller.cli",
		"com.excilys.cdb.dao",
		"com.excilys.cdb.service",
		"com.excilys.cdb.ui",
})
public class LaunchConsole {
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
}
