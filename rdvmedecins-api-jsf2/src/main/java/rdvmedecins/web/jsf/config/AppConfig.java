package rdvmedecins.web.jsf.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import rdvmedecins.config.DomainAndPersistenceConfig;

/**
 * on importe la classe [DomainAndPersistenceConfig] qui configure le projet
 * [rdvmedecins-api-core] afin d'avoir accès aux beans de ce projet ;
 * 
 * @author Malick
 *
 */

// @EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = { "rdvmedecins.web.jsf" })
//@ImportResource({ "classpath:spring-config-metier-dao.xml" })
@Import({ DomainAndPersistenceConfig.class })
public class AppConfig {

}
