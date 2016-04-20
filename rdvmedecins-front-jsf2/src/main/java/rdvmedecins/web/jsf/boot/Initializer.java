package rdvmedecins.web.jsf.boot;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Initializer implements ServletContextInitializer {
 
  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
      System.err.println("------------------ Initializer setServletContext - onStartup ------------------");
	  /*
		servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
		// Use JSF view templates saved as *.xhtml, for use with Facelets
		servletContext.setInitParameter("javax.faces.DEFAULT_SUFFIX", ".xhtml");
		servletContext.setInitParameter("javax.faces.PARTIAL_STATE_SAVING_METHOD", "true");
		// Enable special Facelets debug output during development
		servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
		servletContext.setInitParameter("facelets.DEVELOPMENT", "true");
		// Causes Facelets to refresh templates during development
		servletContext.setInitParameter("javax.faces.FACELETS_REFRESH_PERIOD", "1");
		servletContext.setInitParameter("encoding", "UTF-8");
		servletContext.setInitParameter("javax.faces.FACELETS_REFRESH_PERIOD", "1");
		
      servletContext.setInitParameter("primefaces.THEME", "bootstrap");
      servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", Boolean.TRUE.toString());
      servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", Boolean.TRUE.toString());
      servletContext.setInitParameter("primefaces.FONT_AWESOME", Boolean.TRUE.toString());
      */
  }

  
}