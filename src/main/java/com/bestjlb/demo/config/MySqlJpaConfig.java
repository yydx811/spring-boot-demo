package com.bestjlb.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Created by yydx811 on 2017/10/26.
 */
@Configuration
@EnableJpaRepositories("com.bestjlb.demo.repository")
@EnableTransactionManagement
public class MySqlJpaConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        final DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(env.getProperty("demo.mysql.driver", "com.mysql.jdbc.Driver"));
        dataSource.setUrl(env.getProperty("demo.mysql.url", "jdbc:mysql://10.10.10.10:3306/jlb-server?useSSL=false"));
        dataSource.setUsername(env.getProperty("demo.mysql.username", "jlb-test"));
        dataSource.setPassword(env.getProperty("demo.mysql.password", "jlb-test-123"));
        dataSource.setMaxActive(200);
        dataSource.setInitialSize(10);
        dataSource.setMinIdle(10);
        dataSource.setMaxWait(60 * 1000L);
        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(false);
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource());
        factory.setPackagesToScan("com.bestjlb.demo.meta");
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        final JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(emf);
        return txManager;
    }
}
