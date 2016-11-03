package com.mindfire.review;

import javax.servlet.Filter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * This Class registers a ContextLoaderlistener (optionally) and a
 * DispatcherServlet and allows us to easily add configuration classes to load
 * for both classes and to apply filters to the DispatcherServlet and to provide
 * the servlet mapping. For bootstrapping the application.
 * 
 * @author pratyasa
 *
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	// the parent context for any DispatcherServlet application contexts. As
	// such, it typically contains middle-tier services, data sources, etc.
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { ApplicationConfig.class };
	}

	// returns the configuration classes for the dispatcher servlet application
	// context or null if all configuration is specified through root config
	// classes.
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	// Registering a chain of filters. A servlet filter lets us intercepts
	// requests and responses on the web application
	@Override
	protected Filter[] getServletFilters() {

		return new Filter[] { new HiddenHttpMethodFilter(), new MultipartFilter(),
				new OpenEntityManagerInViewFilter() };
	}

}