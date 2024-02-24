package com.example.sharddemo.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        basePackages = "com.example.sharddemo.repository.sourceone",
        entityManagerFactoryRef = "oneEntityManager",
        transactionManagerRef = "oneTransactionManager"
)
public class SourceOnePersistenceConfiguration {

    private final String ONE_PACKAGE_SCAN = "com.example.sharddemo.entity";

    @Bean(name = "oneDataSourceProperties")
    @ConfigurationProperties("app.datasource.source-one")
    public DataSourceProperties oneDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "oneHikariProperties")
    @ConfigurationProperties("app.datasource.source-one.hikari")
    public HikariProperties oneHikariProperties() {
        return new HikariProperties();
    }

    @Bean(name = "oneHikariDataSource")
    public DataSource oneHikariDataSource() {
        var oneDataSource = oneDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
        oneDataSource.setMaximumPoolSize(oneHikariProperties().getMaximumPoolSize());
        try {
            oneDataSource.getConnection();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return oneDataSource;
    }

    @Bean(name = "oneEntityManager")
    public LocalContainerEntityManagerFactoryBean oneEntityManager() {
        var em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(oneHikariDataSource());
        em.setPackagesToScan(ONE_PACKAGE_SCAN);
        var vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        return em;
    }

    @Bean(name = "oneTransactionManager")
    public PlatformTransactionManager oneTransactionManager() {
        var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                oneEntityManager().getObject()
        );
        return transactionManager;
    }

}
