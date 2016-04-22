package rdvmedecins.springthymeleaf.server.requests;

import java.util.List;
import java.util.Locale;

import org.springframework.web.context.WebApplicationContext;
import org.thymeleaf.context.WebContext;

public class ActionContext {

	// data
	private WebContext thymeleafContext;
	private WebApplicationContext springContext;
	private Locale locale;
	private List<String> erreurs;

	// constructeurs
	public ActionContext() {

	}

	public ActionContext(WebContext thymeleafContext, WebApplicationContext springContext, Locale locale,
			List<String> erreurs) {
		this.thymeleafContext = thymeleafContext;
		this.springContext = springContext;
		this.locale = locale;
		this.erreurs = erreurs;
	}

	// getters et setters
	public WebContext getThymeleafContext() {
		return thymeleafContext;
	}

	public void setThymeleafContext(WebContext thymeleafContext) {
		this.thymeleafContext = thymeleafContext;
	}

	public WebApplicationContext getSpringContext() {
		return springContext;
	}

	public void setSpringContext(WebApplicationContext springContext) {
		this.springContext = springContext;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public List<String> getErreurs() {
		return erreurs;
	}

	public void setErreurs(List<String> erreurs) {
		this.erreurs = erreurs;
	}

}
