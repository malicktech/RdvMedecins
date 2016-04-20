package rdvmedecins.web.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	// composant qui indique si on accepte ou non les requêtes inter-domaines
	// des clients étrnagers au domaine du serveur
//	@Bean
//	public boolean isCorsEnabled() {
//		return true;
//	}

	// configuration dispatcherservlet pour les headers CORS
	@Bean
	public DispatcherServlet dispatcherServlet() {
		DispatcherServlet servlet = new DispatcherServlet();
		// l'application traite elle-même les demandes HTTP [OPTIONS]
		servlet.setDispatchOptionsRequest(true);
		return servlet;
	}

	/*
	 * mapping jSON le bean [mappingJackson2HttpMessageConverter] va pouvoir
	 * être injecté dans le contrôleur, ce qui va nous permettre de contrôler la
	 * sérialisation des objets rendus par le service web
	 */
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		final ObjectMapper objectMapper = new ObjectMapper();
		converter.setObjectMapper(objectMapper);
		return converter;
	}

	/*
	 * ajoutent un convertisseur jSON à la liste des convertisseurs de
	 * l'application web. La bibliothèque jSON Jackson étant dans le Classpath
	 * du projet, c'est elle qui est utilisée par défaut. Si on ne faisait rien,
	 * Spring Boot ferait la même chose par défaut. Alors pourquoi le faire ?
	 * Nous allons découvrir que nous avons besoin de contrôler la sérialisation
	 * des objets rendus par le service web / jSON. Si on ne fait rien, la
	 * bibliothèque jSON sérialise la totalité de l'objet. Or nous verrons que
	 * dans certains cas, ce n'est pas possible. Pour pouvoir contrôler cette
	 * sérialisation, nous avons besoin du bean
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(mappingJackson2HttpMessageConverter());
		super.configureMessageConverters(converters);
	}

}
