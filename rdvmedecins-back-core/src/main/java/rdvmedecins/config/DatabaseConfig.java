package rdvmedecins.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
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

import liquibase.integration.spring.SpringLiquibase;
import rdvmedecins.config.liquibase.AsyncSpringLiquibase;

//@PropertySource({ "classpath:application.properties" })
//@EnableAutoConfiguration
//@EntityScan(basePackages = { "rdvmedecins.domain"})

/**
 * Database access confguration
 * 
 * JpaVendorAdapter (provider jpa), EntityManagerFactory and
 * PlatformTransactionManager are autoconfigured in spring boot properties
 * 
 * @author Malick
 *
 */
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

	/**
	 * Configure Datasource with Hikaripool connection
	 */
	@Bean(destroyMethod = "close")
	@ConditionalOnExpression("#{!environment.acceptsProfiles('cloud') && !environment.acceptsProfiles('heroku')}")
	public DataSource dataSource(DataSourceProperties dataSourceProperties, AppProperties appProperties) {
		log.debug("Configuring Datasource ...");

		if (dataSourceProperties.getUrl() == null) {
			log.error(
					"Your database connection pool configuration is incorrect! The application"
							+ " cannot start. Please check your Spring profile, current profiles are: {}",
					Arrays.toString(env.getActiveProfiles()));
			throw new ApplicationContextException("Database connection pool is not configured correctly");
		}

		HikariConfig config = new HikariConfig();
		config.setDataSourceClassName(dataSourceProperties.getDriverClassName());
		config.addDataSourceProperty("url", dataSourceProperties.getUrl());
		if (dataSourceProperties.getUsername() != null) {
			config.addDataSourceProperty("user", dataSourceProperties.getUsername());
		} else {
			config.addDataSourceProperty("user", "");
		}
		if (dataSourceProperties.getPassword() != null) {
			config.addDataSourceProperty("password", dataSourceProperties.getPassword());
		} else {
			config.addDataSourceProperty("password", "");
		}
		// MySQL optimizations, see
		// https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration
		if ("com.mysql.jdbc.jdbc2.optional.MysqlDataSource".equals(dataSourceProperties.getDriverClassName())) {
			config.addDataSourceProperty("cachePrepStmts", appProperties.getDatasource().isCachePrepStmts());
			config.addDataSourceProperty("prepStmtCacheSize", appProperties.getDatasource().getPrepStmtCacheSize());
			config.addDataSourceProperty("prepStmtCacheSqlLimit",
					appProperties.getDatasource().getPrepStmtCacheSqlLimit());
		}
		if (metricRegistry != null) {
			config.setMetricRegistry(metricRegistry);
		}
		return new HikariDataSource(config);
	}

	/**
	 * Liquibase configuration
	 * 
	 * @param dataSource
	 * @param dataSourceProperties
	 *            springboot properties from application.yml
	 * @param liquibaseProperties
	 *            springboot properties from application.yml
	 * @return
	 */
	@Bean
	public SpringLiquibase liquibase(DataSource dataSource, DataSourceProperties dataSourceProperties,
			LiquibaseProperties liquibaseProperties) {
		// Use liquibase.integration.spring.SpringLiquibase if you don't want
		// Liquibase to start asynchronously
		SpringLiquibase liquibase = new AsyncSpringLiquibase();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog(liquibaseProperties.getChangeLog());
		liquibase.setContexts(liquibaseProperties.getContexts());
		liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
		liquibase.setDropFirst(liquibaseProperties.isDropFirst());
		liquibase.setShouldRun(liquibaseProperties.isEnabled());
		if (env.acceptsProfiles(Constants.SPRING_PROFILE_FAST)) {
			if ("org.h2.jdbcx.JdbcDataSource".equals(dataSourceProperties.getDriverClassName())) {
				liquibase.setShouldRun(true);
				log.warn(
						"Using '{}' profile with H2 database in memory is not optimal, you should consider switching to"
								+ " MySQL or Postgresql to avoid rebuilding your database upon each start.",
						Constants.SPRING_PROFILE_FAST);
			} else {
				liquibase.setShouldRun(false);
			}
		} else {
			log.debug("Configuring Liquibase");
		}
		return liquibase;
	}

	/**
	 * Hibernate4Module to add in MappingJackson2HttpMessageConverter to Avoid
	 * Jackson serialization on non fetched lazy objects
	 */
	@Bean
	public Hibernate4Module hibernate4Module() {
		return new Hibernate4Module();
	}

}
