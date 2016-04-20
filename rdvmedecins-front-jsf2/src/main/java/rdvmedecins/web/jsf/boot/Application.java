package rdvmedecins.web.jsf.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import rdvmedecins.config.DomainAndPersistenceConfig;
import rdvmedecins.web.config.SecurityConfig;
import rdvmedecins.web.jsf.config.JsfConfig;

/**
 * on importe la classe [DomainAndPersistenceConfig] qui configure le projet
 * [rdvmedecins-api-core] afin d'avoir accès aux beans de ce projet ;
 * 
 * @author Malick
 *
 */
@EnableAutoConfiguration
@Import({ DomainAndPersistenceConfig.class, JsfConfig.class, SecurityConfig.class})
@ComponentScan(basePackages = { "rdvmedecins.web.jsf" })
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		System.err.println("------------------ Application - main ------------------");
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		System.err.println("------------------ Application - configure SpringApplicationBuilder  ------------------");
		return application.sources(Application.class, Initializer.class);
	}

}
