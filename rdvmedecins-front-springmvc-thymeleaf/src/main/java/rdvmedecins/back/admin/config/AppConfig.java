package rdvmedecins.back.admin.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import rdvmedecins.config.DatabaseConfig;

@Configuration
//@EnableAutoConfiguration
@Import({ DatabaseConfig.class, WebMvcConfig.class, SecurityConfig.class})
//@ComponentScan(basePackages = { "rdvmedecins.back.admin" })
public class AppConfig {


}
