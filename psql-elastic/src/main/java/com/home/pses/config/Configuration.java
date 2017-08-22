package com.home.pses.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
@EnableElasticsearchRepositories(basePackages = "com.home.pses.search")
@EnableJpaRepositories(basePackages = "com.home.pses.repositories")
public class Configuration {

    private final String persistenceUnitName = "psql";

    @Value("${datasource.driver.classname}")
    private String dataSourceDriverClassName;

    @Value("${datasource.url}")
    private String dataSourceUrl;

    @Value("${datasource.username}")
    private String dataSourceUsername;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dataSourceDriverClassName);
        dataSource.setUrl(dataSourceUrl);
        dataSource.setUsername(dataSourceUsername);
        return dataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
        entityManagerFactory.setPackagesToScan("com.home.pses.entity", "com.home.pses.repositories");
        entityManagerFactory.setPersistenceUnitName(persistenceUnitName);
        return entityManagerFactory;
    }
}