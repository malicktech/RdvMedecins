package rdvmedecins.springthymeleaf.server.boot;

import org.springframework.boot.SpringApplication;

import rdvmedecins.springthymeleaf.server.config.AppConfig;

public class Boot {

	public static void main(String[] args) {
		SpringApplication.run(AppConfig.class, args);
	}
}
