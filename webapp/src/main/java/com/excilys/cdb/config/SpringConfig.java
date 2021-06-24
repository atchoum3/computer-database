package com.excilys.cdb.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import config.PersistenceConfig;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;



@Configuration
@EnableWebMvc
@Import(PersistenceConfig.class)
@ComponentScan(basePackages =  {
		"com.excilys.cdb.advice",
		"com.excilys.cdb.binding.api.mapper",
		"com.excilys.cdb.binding.persistence.mapper",
		"com.excilys.cdb.binding.web.mapper",
		"com.excilys.cdb.binding.web.validator",
		"com.excilys.cdb.config",
		"com.excilys.cdb.controller.api",
		"com.excilys.cdb.controller.web",
		"com.excilys.cdb.dao",
		"com.excilys.cdb.service",
		})
public class SpringConfig implements WebMvcConfigurer {

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

}
