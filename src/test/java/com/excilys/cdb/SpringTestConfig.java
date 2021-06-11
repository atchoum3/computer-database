package com.excilys.cdb;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.excilys.cdb.config.CliConfig;

@Configuration
@Import({CliConfig.class})
public class SpringTestConfig {

}
