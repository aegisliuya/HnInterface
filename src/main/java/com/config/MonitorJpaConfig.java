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
import java.util.List;

/**
 * Created by zms on 2016/7/13.
 */
@Configuration
@ImportResource("classpath:properties-config.xml")
@EnableJpaRepositories(transactionManagerRef = "monitorTrasactionManager", entityManagerFactoryRef = "monitorEntityManagerFactory",
        basePackages = "com.engine.model.dao.repository.jpa.monitor", repositoryBaseClass = CommonRepositoryImpl.class)
public class MonitorJpaConfig extends DBConfig{
    @Value("${bean_path_140}")
    private String path;
    @Value("${url_140}")
    private String url;
    @Value("${user_140}")
    private String username;
    @Value("${pass_140}")
    private String password;

    @Bean(name = "monitorDataSource")
    public DataSource dataSource() {
        BasicDataSource dataSource = buildDataSource(url, username, password);
        dataSource.setTestOnBorrow(true);
        dataSource.setValidationQuery("SELECT count(id) FROM monitor_sys_contact");
        return dataSource;
    }

    @Bean(name = "monitorTrasactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());

        return txManager;
    }

    @Bean(name = "monitorEntityManagerFactory")
    public EntityManagerFactory entityManagerFactory() {
        return buildEntityManagerFactory(path);
    }
}
