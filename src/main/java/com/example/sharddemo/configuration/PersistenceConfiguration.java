package com.example.sharddemo.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.sharddemo.repository",
        entityManagerFactoryRef = "multiEntityManager",
        transactionManagerRef = "multiTransactionManager"
)
public class PersistenceConfiguration {

    private final String PACKAGE_SCAN = "com.example.sharddemo.entity";

    @Primary
    @Bean(name = "defaultDataSourceProperties")
    @ConfigurationProperties("app.datasource.source-default")
    public DataSourceProperties defaultDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "oneDataSourceProperties")
    @ConfigurationProperties("app.datasource.source-one")
    public DataSourceProperties oneDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "twoDataSourceProperties")
    @ConfigurationProperties("app.datasource.source-two")
    public DataSourceProperties twoDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "defaultHikariProperties")
    @Primary
    @ConfigurationProperties("app.datasource.source-default.hikari")
    public HikariProperties defaultHikariProperties() {
        return new HikariProperties();
    }

    @Bean(name = "oneHikariProperties")
    @Primary
    @ConfigurationProperties("app.datasource.source-one.hikari")
    public HikariProperties oneHikariProperties() {
        return new HikariProperties();
    }

    @Bean(name = "twoHikariProperties")
    @Primary
    @ConfigurationProperties("app.datasource.source-two.hikari")
    public HikariProperties twoHikariProperties() {
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

    @Bean(name = "multiRoutingDataSource")
    public DataSource multiRoutingDataSource() {
        var targetDataSources = new HashMap<>();
        targetDataSources.put(DBSourceEnum.DEFAULT, defaultHikariDataSource());
        targetDataSources.put(DBSourceEnum.SOURCE_ONE, oneHikariDataSource());
        targetDataSources.put(DBSourceEnum.SOURCE_TWO, twoHikariDataSource());
        var multiRoutingDataSource = new MultiRoutingDataSource();
        multiRoutingDataSource.setDefaultTargetDataSource(defaultHikariDataSource());
        multiRoutingDataSource.setTargetDataSources(targetDataSources);
        return multiRoutingDataSource;
    }

    @Bean(name = "multiEntityManager")
    public LocalContainerEntityManagerFactoryBean multiEntityManager() {
        var em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(multiRoutingDataSource());
        em.setPackagesToScan(PACKAGE_SCAN);
        var vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
//        em.setJpaProperties(hibernateProperties());
        return em;
    }

    @Bean(name = "multiTransactionManager")
    public PlatformTransactionManager multiTransactionManager() {
        var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                multiEntityManager().getObject()
        );
        return transactionManager;
    }

    @Primary
    @Bean(name = "dbSessionFactory")
    public LocalSessionFactoryBean dbSessionFactory() {
        var sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(multiRoutingDataSource());
        sessionFactoryBean.setPackagesToScan(PACKAGE_SCAN);
//        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        return sessionFactoryBean;
    }

//    private Properties hibernateProperties() {
//        var properties = new Properties();
//        properties.put("hibernate.show_sql", true);
//        properties.put("hibernate.batch_size", 50);
//        properties.put("hibernate.order_inserts", true);
//        properties.put("hibernate.order_updates", true);
//        properties.put("hibernate.jdbc.batch_versioned_data", true);
//        return properties;
//    }

}
