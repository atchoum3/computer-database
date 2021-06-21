package com.excilys.cdb;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;



@Configuration
@EnableWebMvc
@ComponentScan(basePackages =  {
		"com.excilys.cdb.controller.web",
		"com.excilys.cdb.bindingFront.mapper",
		"com.excilys.cdb.bindingFront.validator",
		"com.excilys.cdb.service",
		"com.excilys.cdb.ui",
		"com.excilys.cdb.bindingBack.mapper",
		"com.excilys.cdb.dao",
		"com.excilys.cdb.dao.mapper",
		})
public class MainWebAppInitializer implements WebApplicationInitializer, WebMvcConfigurer {
	private static Logger logger = LoggerFactory.getLogger(MainWebAppInitializer.class);
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

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(MainWebAppInitializer.class);
		context.setServletContext(servletContext);

        ServletRegistration.Dynamic appServlet = servletContext.addServlet("springmvc", new DispatcherServlet(context));
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/");
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

	@Bean
	public PlatformTransactionManager txManager() {
	    return new DataSourceTransactionManager(dataSource());
	}

	private final Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		return hibernateProperties;
	}
}
