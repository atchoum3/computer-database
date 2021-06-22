package com.excilys.cdb.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SpringConfig.class);
        context.setServletContext(servletContext);

        DispatcherServlet dispatcher = new DispatcherServlet(context);
        dispatcher.setThrowExceptionIfNoHandlerFound(true);
        
        Dynamic servlet = servletContext.addServlet("dispatcher", dispatcher);
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
	}

}