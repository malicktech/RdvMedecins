package rdvmedecins.web.jsf.config;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;

import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;

import com.sun.faces.config.ConfigureListener;

/**
 * Configuring faces servlet JSF is just a servlet so both JSF and spring MVC
 * can co-exist
 */
@Configuration
public class JsfConfig implements ServletContextAware {

	@Bean
	public ServletRegistrationBean facesServletRegistration() {
		System.err.println("----------------- facesServletRegistration -------------------");
		ServletRegistrationBean registration = new ServletRegistrationBean(new FacesServlet(),
				new String[] { "*.xhtml", "*.faces", "*.jsf" });
		registration.setName("FacesServlet");
		registration.setLoadOnStartup(1);
		return registration;
	}

	@Bean
	public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
		System.err.println("----------------- jsfConfigureListener -------------------");
		return new ServletListenerRegistrationBean<ConfigureListener>(new ConfigureListener());
	}
	
	/**
	 * Add facelet parameter 
	 * 
	 * To get JSF working on Spring Boot without a web.xml or faces-config.xml,
	 * need to force it to load its configuration files via an init parameter on
	 * the ServletContext. An easy way to do that is to implement
	 * ServletContextAware:
	 * 
	 */
	@Override
	public void setServletContext(ServletContext servletContext) {
		System.err.println("----------------- setServletContext -------------------");
		servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
		// Use JSF view templates saved as *.xhtml, for use with Facelets
		servletContext.setInitParameter("javax.faces.DEFAULT_SUFFIX", ".xhtml");
		servletContext.setInitParameter("javax.faces.PARTIAL_STATE_SAVING_METHOD", "true");
		// Enable special Facelets debug output during development
		servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
		servletContext.setInitParameter("facelets.DEVELOPMENT", "true");
		// Causes Facelets to refresh templates during development
		servletContext.setInitParameter("javax.faces.FACELETS_REFRESH_PERIOD", "1");
        servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", Boolean.TRUE.toString());

		servletContext.setInitParameter("encoding", "UTF-8");
		
        servletContext.setInitParameter("primefaces.THEME", "bootstrap");
        servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", Boolean.TRUE.toString());
        servletContext.setInitParameter("primefaces.FONT_AWESOME", Boolean.TRUE.toString());
        servletContext.setInitParameter("primefaces.UPLOADER", "commons");
        

		// Declare Spring Security Facelets tag library
		// servletContext.setInitParameter("javax.faces.FACELETS_LIBRARIES",
		// "/WEB-INF/springsecurity.taglib.xml");
	}

}
