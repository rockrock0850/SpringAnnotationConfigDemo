package com.promeritage.annotation.demo.configs;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.Log4jConfigListener;

public class ApplicationInitializer implements WebApplicationInitializer {

	/*
	 * Called first when the application starts loading
	 */
	public void onStartup(ServletContext servletContext) throws ServletException {
		System.out.println("Inside application initializer...");

		/*
		 * Registering the class that incorporates the annotated
		 * DispatcherServlet configuration of spring
		 */
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(Config.class);

		/*
		 * Adding the listener for the rootContext
		 */
		servletContext.addListener(new ContextLoaderListener(rootContext));

		servletContext.setInitParameter("log4jConfigLocation", "classpath:properties/log4j.properties");
		servletContext.addListener(Log4jConfigListener.class);

		/*
		 * Registering the dispatcher servlet mappings
		 */
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");

		/*
		 * Adding filter
		 */
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("utf-8");
		characterEncodingFilter.setForceEncoding(true);

		/*
		 * Registering the dispatcher filter mappings
		 */
		FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter", characterEncodingFilter);
		encodingFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), true, "/*");
	}

}