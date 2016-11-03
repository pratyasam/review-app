package com.mindfire.review;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * An annotation configuration file.
 * @author pratyasa
 *
 */
@Configuration
@EnableWebMvc
@PropertySource("classpath:persistence.properties")
@PropertySource("classpath:application.properties")
@ComponentScan(basePackageClasses = ApplicationConfig.class)
class ApplicationConfig {

	// PropertySourcesPlaceholderConfigureris a BeanFactoryPostProcessor
	// responsible for scanning through all the registered bean definitions and
	// injecting in the resolved place holders. It resolves ${} in @Values
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		
		return new PropertySourcesPlaceholderConfigurer();
	}

}