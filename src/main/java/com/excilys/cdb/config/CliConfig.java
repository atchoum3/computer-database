package com.excilys.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SpringConfig.class})
@ComponentScan(basePackages =  {"com.excilys.cdb.controller.cli"})
public class CliConfig {

}
