package com.excilys.cdb.target;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.config.CliConfig;
import com.excilys.cdb.controller.cli.ConsoleControler;

public class LaunchConsole {
	private static Logger logger = LoggerFactory.getLogger(LaunchConsole.class);
	private static ApplicationContext context;

	public static void main(String[] args) {
		try {
			context = new AnnotationConfigApplicationContext(CliConfig.class);
			ConsoleControler console = context.getBean(ConsoleControler.class);
			console.start();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
