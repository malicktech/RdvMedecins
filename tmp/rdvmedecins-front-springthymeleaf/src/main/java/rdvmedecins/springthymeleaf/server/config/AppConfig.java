package rdvmedecins.springthymeleaf.server.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import rdvmedecins.client.config.DaoConfig;

@EnableAutoConfiguration
@ComponentScan(basePackages = { "rdvmedecins.springthymeleaf.server" })
@Import({ WebConfig.class, DaoConfig.class })
public class AppConfig {

	// admin / admin
	private final String USER_INIT = "admin";
	private final String MDP_USER_INIT = "admin";
	// racine service web / json
	private final String WEBJSON_ROOT = "http://localhost:8080";
	// timeout en millisecondes
	private final int TIMEOUT = 5000;
	// CORS
	private final boolean CORS_ALLOWED=true;

	public int getTIMEOUT() {
		return TIMEOUT;
	}

	public String getWEBJSON_ROOT() {
		return WEBJSON_ROOT;
	}

	// getters et setters
	public String getUSER_INIT() {
		return USER_INIT;
	}

	public String getMDP_USER_INIT() {
		return MDP_USER_INIT;
	}

	public boolean isCORS_ALLOWED() {
		return CORS_ALLOWED;
	}
	
}
