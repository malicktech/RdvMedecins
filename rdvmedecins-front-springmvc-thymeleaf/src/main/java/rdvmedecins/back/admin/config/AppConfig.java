package rdvmedecins.back.admin.config;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import rdvmedecins.config.DomainAndPersistenceConfig;

@Configuration
@EnableAutoConfiguration
@Import({ DomainAndPersistenceConfig.class, WebMvcConfig.class, SecurityConfig.class})
@ComponentScan(basePackages = { "rdvmedecins.back.admin" })
public class AppConfig {


}
