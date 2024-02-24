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
        basePackages = "com.example.sharddemo.repository.sourcetwo",
        entityManagerFactoryRef = "twoEntityManager",
        transactionManagerRef = "twoTransactionManager"
)
public class SourceTwoPersistenceConfiguration {
    private final String TWO_PACKAGE_SCAN = "com.example.sharddemo.entity.sourcetwo";
    @Bean(name = "twoDataSourceProperties")
    @ConfigurationProperties("app.datasource.source-two")
    public DataSourceProperties twoDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "twoHikariProperties")
    @ConfigurationProperties("app.datasource.source-two.hikari")
    public HikariProperties twoHikariProperties() {
        return new HikariProperties();
    }

    @Bean(name = "twoHikariDataSource")
    public DataSource twoHikariDataSource() {
        var twoDataSource = twoDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
        twoDataSource.setMaximumPoolSize(twoHikariProperties().getMaximumPoolSize());
        try {
            twoDataSource.getConnection();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return twoDataSource;
    }

    @Bean(name = "twoEntityManager")
    public LocalContainerEntityManagerFactoryBean twoEntityManager() {
        var em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(twoHikariDataSource());
        em.setPackagesToScan(TWO_PACKAGE_SCAN);
        var vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        return em;
    }

    @Bean(name = "twoTransactionManager")
    public PlatformTransactionManager twoTransactionManager() {
        var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                twoEntityManager().getObject()
        );
        return transactionManager;
    }

}
