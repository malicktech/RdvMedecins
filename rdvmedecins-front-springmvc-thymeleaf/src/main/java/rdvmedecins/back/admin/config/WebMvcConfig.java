package rdvmedecins.back.admin.config;

import java.util.Locale;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	/*
	 * RessourceHandler configuration
	 * =========================================================================
	 */

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	}

	/*
	 * View conroller configuration
	 * =========================================================================
	 * set up view controllers for index and login
	 */
	/*
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// registry.addViewController("/index").setViewName("index");
		// registry.addViewController("/").setViewName("home");
		// registry.addViewController("/login").setViewName("login");
	}
	*/



	/*
	 * Error page configuration
	 * =========================================================================
	 * It creates three ErrorPage instances for three common HTTP Status Codes
	 * and then adds them to the container
	 */

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {

		return (container -> {
			ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
			ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
			ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");

			container.addErrorPages(error401Page, error404Page, error500Page);
		});
	}

}
