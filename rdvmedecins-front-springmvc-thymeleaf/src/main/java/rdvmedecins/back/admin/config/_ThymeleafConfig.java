package rdvmedecins.back.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import nz.net.ultraq.thymeleaf.LayoutDialect;

//@Configuration
//@EnableWebMvc
public class _ThymeleafConfig extends WebMvcConfigurerAdapter {
	
	/* Config DISABLED - Autoconfig used */
	
	/*
	 * Thymeleaf View resolver configuration
	 * =========================================================================
	 */

	@Bean
	public TemplateResolver templateResolver() {
//		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();

		templateResolver.setPrefix("classpath:/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("LEGACYHTML5");
		templateResolver.setCacheable(false);
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setOrder(0);

		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
     // templateEngine.addTemplateResolver(urlTemplateResolver());
     		 templateEngine.setDialect(new LayoutDialect());
     		 templateEngine.addDialect(new SpringSecurityDialect());
        return templateEngine;
    }
	
	@Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(templateEngine());
        return thymeleafViewResolver;
    }

	


}
