package com.excilys.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import config.PersistenceConfig;


@Configuration
@Import(PersistenceConfig.class)
@ComponentScan(basePackages =  {
		"com.excilys.cdb.binding.persistence.mapper",
		"com.excilys.cdb.dao",
})
public class SpringTestConfig {
}
