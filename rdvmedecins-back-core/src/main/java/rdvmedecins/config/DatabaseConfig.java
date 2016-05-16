package rdvmedecins.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


//@PropertySource({ "classpath:application.properties" })
//@EnableAutoConfiguration
//@EntityScan(basePackages = { "rdvmedecins.domain"})


@Configuration
@ComponentScan(basePackages = { "rdvmedecins" })
@EnableJpaRepositories("rdvmedecins.repository")
@EnableTransactionManagement
public class DatabaseConfig {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);
   
	@Autowired
    private Environment env;
	
	@Autowired(required = false)
    private MetricRegistry metricRegistry;
	
    @Bean(destroyMethod = "close")
    @ConditionalOnExpression("#{!environment.acceptsProfiles('cloud') && !environment.acceptsProfiles('heroku')}")
    public DataSource dataSource(DataSourceProperties dataSourceProperties, AppProperties appProperties) {
        log.debug("Configuring Datasource ...");
        
        if (dataSourceProperties.getUrl() == null) {
            log.error("Your database connection pool configuration is incorrect! The application" +
                    " cannot start. Please check your Spring profile, current profiles are: {}",
                Arrays.toString(env.getActiveProfiles()));

            throw new ApplicationContextException("Database connection pool is not configured correctly");
        }
        
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(dataSourceProperties.getDriverClassName());
        config.addDataSourceProperty("url", dataSourceProperties.getUrl());
        if (dataSourceProperties.getUsername() != null) {
            config.addDataSourceProperty("user", dataSourceProperties.getUsername());
        } else {
            config.addDataSourceProperty("user", ""); // HikariCP doesn't allow null user
        }
        if (dataSourceProperties.getPassword() != null) {
            config.addDataSourceProperty("password", dataSourceProperties.getPassword());
        } else {
            config.addDataSourceProperty("password", ""); // HikariCP doesn't allow null password
        }

        //MySQL optimizations, see https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration
        if ("com.mysql.jdbc.jdbc2.optional.MysqlDataSource".equals(dataSourceProperties.getDriverClassName())) {
            config.addDataSourceProperty("cachePrepStmts", appProperties.getDatasource().isCachePrepStmts());
            config.addDataSourceProperty("prepStmtCacheSize", appProperties.getDatasource().getPrepStmtCacheSize());
            config.addDataSourceProperty("prepStmtCacheSqlLimit", appProperties.getDatasource().getPrepStmtCacheSqlLimit());
        }       
        if (metricRegistry != null) {
            config.setMetricRegistry(metricRegistry);
        }
        return new HikariDataSource(config);
    }
	
	 /** 
	  * Hibernate4Module to  add in MappingJackson2HttpMessageConverter  to Avoid Jackson serialization on non fetched lazy objects
	  */
    @Bean
    public Hibernate4Module hibernate4Module() {
        return new Hibernate4Module();
    }
    
    
	/** 
	 * Datasource, JpaVendorAdapter (provider jpa), EntityManagerFactory and PlatformTransactionManager are configured in spring boot properties 
	 */
	
	
	/*
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/dbrdvmedecins2");
		dataSource.setUsername("admin");
		dataSource.setPassword("admin");
		return dataSource;
	}
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(false);
		hibernateJpaVendorAdapter.setGenerateDdl(false);
		hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
		return hibernateJpaVendorAdapter;
	}	
	@Bean
	public EntityManagerFactory entityManagerFactory(JpaVendorAdapter jpaVendorAdapter, DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(jpaVendorAdapter);
		factory.setPackagesToScan("demo.entities");
		factory.setDataSource(dataSource);
		factory.afterPropertiesSet();
		return factory.getObject();
	}
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory c) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(c);
		return txManager;
	}
	*/

}
