package com.mindfire.review;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ClassUtils;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * This class defines all the connections to the database via DataDource object
 * and manages the database transactions. Defines the base
 * package to scan for Spring Data repositories
 * 
 * @author pratyasa
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = ApplicationConfig.class)
class JpaConfig {

	@Value("${dataSource.driverClassName}")
	private String driver;
	@Value("${dataSource.url}")
	private String url;
	@Value("${dataSource.username}")
	private String username;
	@Value("${dataSource.password}")
	private String password;
	@Value("${hibernate.dialect}")
	private String dialect;
	@Value("${hibernate.hbm2ddl.auto}")
	private String hbm2ddlAuto;

	// A factory for connections to the physical data source that this
	// DataSource object represents. An alternative to the DriverManager
	// facility, a DataSource object is the preferred means of getting a
	// connection to the database.
	@Bean
	public DataSource dataSource() {

		// Simple implementation of the standard JDBC DataSource interface
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		// Setting the properties
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		return dataSource;
	}

	// Container Managed entity manager. This type of entity manager is most
	// appropriate for use by a Java EE container that wants to maintain
	// some control over JPA configuration beyond what’s specified in
	// persistence.xml and can override the location of the persistence.xml
	// file.
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {

		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		// Specify the JDBC DataSource that the JPA persistence provider is
		// supposed to use for accessing the database.
		entityManagerFactoryBean.setDataSource(dataSource);

		String entities = ClassUtils.getPackageName(ApplicationConfig.class);
		String converters = ClassUtils.getPackageName(Jsr310JpaConverters.class);

		// Set whether to use Spring-based scanning for entity classes in the
		// classpath instead of using JPA's standard scanning of jar files with
		// persistence.xml markers in them.
		entityManagerFactoryBean.setPackagesToScan(entities, converters);

		// Specify the JpaVendorAdapter implementation for the desired JPA
		// provider, if any.
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		// The Properties class represents a persistent set of properties.
		Properties jpaProperties = new Properties();
		jpaProperties.put(AvailableSettings.DIALECT, dialect);
		jpaProperties.put(AvailableSettings.HBM2DDL_AUTO, hbm2ddlAuto);
		jpaProperties.put(AvailableSettings.SHOW_SQL, true);
		entityManagerFactoryBean.setJpaProperties(jpaProperties);

		return entityManagerFactoryBean;
	}

	// Programmatic transaction management approach, which allows us to manage
	// the transaction with the help of programming in the source code.
	// It drives the transaction.
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

		// Set the EntityManagerFactory that this instance should manage
		// transactions for.
		return new JpaTransactionManager(entityManagerFactory);
	}
}
