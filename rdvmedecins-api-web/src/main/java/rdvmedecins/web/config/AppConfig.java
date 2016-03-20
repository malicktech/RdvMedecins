package rdvmedecins.web.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import rdvmedecins.config.DomainAndPersistenceConfig;

/**
 * on importe la classe [DomainAndPersistenceConfig] qui configure le projet
 * [rdvmedecins-api-core] afin d'avoir accès aux beans de ce projet ;
 * 
 * @author Malick
 *
 */

@EnableAutoConfiguration
@ComponentScan(basePackages = { "rdvmedecins.web" })
@Import({ DomainAndPersistenceConfig.class, SecurityConfig.class, WebConfig.class })
public class AppConfig {

}
