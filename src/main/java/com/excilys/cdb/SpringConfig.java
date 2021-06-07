package com.excilys.cdb;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(basePackages =  {
		"com.excilys.cdb.controller.cli",
		"com.excilys.cdb.controller.web",
		"com.excilys.cdb.service",
		"com.excilys.cdb.ui",
		"com.excilys.cdb.bindingFront.mapper",
		"com.excilys.cdb.bindingBack.mapper",
		"com.excilys.cdb.dao",
		"com.excilys.cdb.model.mapper",
		})
public class SpringConfig {
}
