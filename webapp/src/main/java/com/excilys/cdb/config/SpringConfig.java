package com.excilys.cdb.config;

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
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@EnableTransactionManagement
@Configuration
@EnableWebMvc
@EnableWebSecurity
@ComponentScan(basePackages =  {
		"com.excilys.cdb.advice",
		"com.excilys.cdb.bindingBack.mapper",
		"com.excilys.cdb.bindingFront.mapper",
		"com.excilys.cdb.bindingFront.validator",
		"com.excilys.cdb.config",
		"com.excilys.cdb.controller.web",
		"com.excilys.cdb.dao",
		"com.excilys.cdb.service",
		})
public class SpringConfig implements WebMvcConfigurer {
	private static Logger logger = LoggerFactory.getLogger(SpringConfig.class);
	private static final String FILE_NAME_CONF_DB = "db.properties";

 	@Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/view/", ".jsp");
    }

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("/lang/message");
		messageSource.setDefaultEncoding("ISO-8859-1");
		return messageSource;
	}

	@Bean
	public LocaleResolver localeResolver() {
		AcceptHeaderLocaleResolver slr = new AcceptHeaderLocaleResolver();
	    slr.setDefaultLocale(Locale.ENGLISH);
		return slr;
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
	public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.excilys.cdb.bindingBack");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

	private final Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		return hibernateProperties;
	}
}
