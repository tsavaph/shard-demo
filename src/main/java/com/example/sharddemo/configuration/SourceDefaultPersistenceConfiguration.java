package com.example.sharddemo.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.sharddemo.repository.sourcedefault",
        entityManagerFactoryRef = "defaultEntityManager",
        transactionManagerRef = "defaultTransactionManager"
)
public class SourceDefaultPersistenceConfiguration {

    private final String DEFAULT_PACKAGE_SCAN = "com.example.sharddemo.entity";

    @Primary
    @Bean(name = "defaultDataSourceProperties")
    @ConfigurationProperties("app.datasource.source-default")
    public DataSourceProperties defaultDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "defaultHikariProperties")
    @Primary
    @ConfigurationProperties("app.datasource.source-default.hikari")
    public HikariProperties defaultHikariProperties() {
        return new HikariProperties();
    }

    @Primary
    @Bean(name = "defaultHikariDataSource")
    public DataSource defaultHikariDataSource() {
        var defaultDataSource = defaultDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
        defaultDataSource.setMaximumPoolSize(defaultHikariProperties().getMaximumPoolSize());
        try {
            defaultDataSource.getConnection();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return defaultDataSource;
    }

    @Primary
    @Bean(name = "defaultEntityManager")
    public LocalContainerEntityManagerFactoryBean defaultEntityManager() {
        var em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(defaultHikariDataSource());
        em.setPackagesToScan(DEFAULT_PACKAGE_SCAN);
        var vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        return em;
    }

    @Primary
    @Bean(name = "defaultTransactionManager")
    public PlatformTransactionManager defaultTransactionManager() {
        var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                defaultEntityManager().getObject()
        );
        return transactionManager;
    }

}
