package com.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by wuhao on 2016/4/20.
 */
public abstract class DBConfig {


	protected BasicDataSource buildDataSource(String url, String username, String password) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}


	protected EntityManagerFactory buildEntityManagerFactory(String path) {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(path);
		factory.setDataSource(this.dataSource());

		factory.setJpaProperties(getHibernateProperties());
		factory.afterPropertiesSet();
		return factory.getObject();
	}

	protected Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put("javax.persistence.validation.mode", "none");
		properties.put("hibernate.jdbc.batch_size", 100);
		properties.put("hibernate.format_sql", false);
		properties.put("hibernate.show_sql", false);
		properties.put("hibernate.generate_statistics", true);
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.generate_statistics", true);
		properties.put("hibernate.cache.use_second_level_cache", true);
		properties.put("hibernate.cache.use_query_cache", true);
		properties.put("hibernate.cache.use_structured_entries", true);
		properties.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		return properties;
	}

	protected abstract DataSource dataSource();


}
