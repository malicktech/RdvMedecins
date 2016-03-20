package rdvmedecins.web.boot;

import org.springframework.boot.SpringApplication;

import rdvmedecins.web.config.AppConfig;

public class Boot {

	public static void main(String[] args) {
		SpringApplication.run(AppConfig.class, args);
	}
}
