package rdvmedecins.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	// composant qui indique si on accepte ou non les requêtes inter-domaines des clients étrnagers au domaine du serveur
	@Bean
	public boolean isCorsEnabled() {
		return true;
	}
		
	// configuration dispatcherservlet pour les headers CORS
	@Bean
	public DispatcherServlet dispatcherServlet() {
		DispatcherServlet servlet = new DispatcherServlet();
		// l'application traite elle-même les demandes HTTP [OPTIONS]
		servlet.setDispatchOptionsRequest(true);
		return servlet;
	}
}
