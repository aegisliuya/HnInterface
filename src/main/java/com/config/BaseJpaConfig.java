package com.config;

import com.engine.model.dao.repository.jpa.CommonRepositoryImpl;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Created by wuhao on 2016/5/13.
 */
@Configuration
@ImportResource("classpath:properties-config.xml")
@EnableJpaRepositories(transactionManagerRef = "imonitorTrasactionManager",
		entityManagerFactoryRef = "imonitorEntityManagerFactory",
		basePackages = {"com.engine.model.dao.repository.jpa.base.config", "com.engine.model.dao.repository.jpa.base.opinion", "com.engine.model.dao.repository.jpa.base.test"},
		repositoryBaseClass = CommonRepositoryImpl.class)
public class BaseJpaConfig extends DBConfig{

	@Value("${bean_path_142}")
	private String path;
	@Value("${url_142}")
	private String url;
	@Value("${user_142}")
	private String username;
	@Value("${pass_142}")
	private String password;


	@Bean(name = "imonitorDataSource")
	public DataSource dataSource() {
		BasicDataSource dataSource = buildDataSource(url, username, password);
		dataSource.setTestOnBorrow(true);
		dataSource.setValidationQuery("SELECT count(id) FROM user");
		return dataSource;
	}

	@Bean(name = "imonitorTrasactionManager")
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());

		return txManager;
	}

	@Bean(name = "imonitorEntityManagerFactory")
	public EntityManagerFactory entityManagerFactory() {
		return buildEntityManagerFactory(path);
	}
}
