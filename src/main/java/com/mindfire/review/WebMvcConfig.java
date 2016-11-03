package com.mindfire.review;

import java.io.File;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * This is the main class providing the configuration behind the MVC Java config.
 * @author pratyasa
 *
 */
@Configuration
class WebMvcConfig extends WebMvcConfigurationSupport {

	private static final String MESSAGE_SOURCE = "/WEB-INF/i18n/messages";
	private static final String VIEWS = "/WEB-INF/views/";

	private static final String RESOURCES_LOCATION = "/resources/";

	// For every Web application one ServletContext object is created by the web
	// container. ServletContext object is used to get configuration information
	// from Deployment Descriptor(web.xml/ApplictaionConfig.java) which will be available 
	// to any servlet or JSPs that are part of the web app.
	@Autowired
	private ServletContext servletContext;

	// for mapping requests to annotated controller methods.
	@Override
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {

		RequestMappingHandlerMapping requestMappingHandlerMapping = super.requestMappingHandlerMapping();
		requestMappingHandlerMapping.setUseSuffixPatternMatch(false);
		requestMappingHandlerMapping.setUseTrailingSlashMatch(false);

		return requestMappingHandlerMapping;
	}

	// Register a ViewResolver to use for view resolution, by resolving views by
	// name. allows for direct resolution of symbolic view names to URLs,
	// without explicitly mapping definition.
	@Bean
	public ViewResolver viewResolver() {

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

		// Set the prefix that gets prepended to view names when building a URL.
		viewResolver.setPrefix(VIEWS);
		// Set the suffix that gets appended to view names when building a URL.
		viewResolver.setSuffix(".jsp");

		return viewResolver;

	}

	// configuring a filter to intercept http form request methods.
	@Bean
	public Filter httpPutFormFilter() {
		HiddenHttpMethodFilter filter = new HiddenHttpMethodFilter();
		filter.setServletContext(servletContext);
		filter.setMethodParam("method");

		return filter;
	}

	// Saves temporary files to the servlet container's temporary directory.
	// Spring based implementation of Apache Commons file upload.
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {

		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(26214400L);

		return multipartResolver;
	}

	// To add resource handlers for serving static resources such as images, css
	// files and others through Spring MVC.
	// Resources can be served out of locations under web application root, from
	// the classpath, and others.
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		String rootPath = System.getProperty("user.home") + "/Desktop/Wotkspace/git";
		String imagePath = "file:" + rootPath + File.separator + "uploads/";
		registry.addResourceHandler("/uploads/**").addResourceLocations(imagePath);
	}

	// Configure a handler to delegate unhandled requests by forwarding to the
	// Servlet container's "default" servlet. Set default servlet handler
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {

		configurer.enable();
	}

}
