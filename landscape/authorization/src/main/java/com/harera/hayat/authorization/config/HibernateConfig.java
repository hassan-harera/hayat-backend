package com.harera.hayat.authorization.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "com.harera.hayat.*",
                transactionManagerRef = "jpaTransactionManager",
                repositoryImplementationPostfix = "Impl")
@EnableTransactionManagement
public class HibernateConfig {

    private static final String[] ENTITY_MANAGER_PACKAGES_TO_SCAN =
                    { "com.harera.hayat.*" };

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {

        String username = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");
        String driverClass = env.getProperty("spring.datasource.driverClassName");
        String url = env.getProperty("spring.datasource.url");

        return DataSourceBuilder.create().username(username).password(password).url(url)
                        .driverClassName(driverClass).build();
    }

    @Bean
    public JpaTransactionManager jpaTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                        new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean
                        .setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(ENTITY_MANAGER_PACKAGES_TO_SCAN);
        entityManagerFactoryBean.setJpaProperties(addProperties());

        return entityManagerFactoryBean;
    }

    private Properties addProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto",
                        env.getProperty("spring.jpa.hibernate.ddl-auto", "none"));
        properties.setProperty("hibernate.show_sql",
                        env.getProperty("spring.jpa.show-sql", "false"));
        properties.setProperty("hibernate.dialect",
                        env.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.setProperty("hibernate.format_sql", env.getProperty(
                        "spring.jpa.properties.hibernate.format_sql", "false"));

        properties.setProperty("hibernate.connection.characterEncoding", env.getProperty(
                        "spring.hibernate.connection.characterEncoding", "utf8"));

        properties.setProperty("hibernate.connection.useUnicode", env
                        .getProperty("spring.hibernate.connection.useUnicode", "true"));

        properties.setProperty("hibernate.connection.charSet",
                        env.getProperty("spring.hibernate.connection.charSet", "UTF-8"));
        return properties;
    }

}
