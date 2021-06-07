package com.excilys.cdb;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {SpringConfig.class}, basePackages =  {
			"com.excilys.cdb.dao",
		})
public class SpringTestConfig {

}