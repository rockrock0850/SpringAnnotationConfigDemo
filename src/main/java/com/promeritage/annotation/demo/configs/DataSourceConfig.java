package com.promeritage.annotation.demo.configs;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 定義了spring的資料庫管理相關機制( 目前使用Spring JPA實作 )
 * @author Adam
 *
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:properties/datasource.properties")
@EnableJpaRepositories(basePackages = "com.promeritage.annotation.demo.module.dao")
public class DataSourceConfig {

	@Autowired
	private Environment env;

	/*
	 * 配置資料庫資料
	 */
	@Bean(name = "dataSource")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
		return dataSource;
	}

	/*
	 * 資料庫管理機制
	 */
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setDataSource(dataSource());
		localContainerEntityManagerFactoryBean.setPackagesToScan("com.promeritage.annotation.demo.module.model");
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		Properties jpaProperties = new Properties();
		jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		jpaProperties.setProperty("hibernate.show_sql", "false");
		jpaProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		localContainerEntityManagerFactoryBean.setJpaProperties(jpaProperties);
		return localContainerEntityManagerFactoryBean;
	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager() {
		EntityManagerFactory entityManagerFactory = entityManagerFactory().getObject();
		return new JpaTransactionManager(entityManagerFactory);
	}

}
