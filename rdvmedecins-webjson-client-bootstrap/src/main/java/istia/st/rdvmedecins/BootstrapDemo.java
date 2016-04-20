package istia.st.rdvmedecins;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;

@EnableAutoConfiguration
@ComponentScan({ "istia.st.rdvmedecins" })
public class BootstrapDemo extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(BootstrapDemo.class, args);
	}

	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setPrefix("classpath:/templates/");
		templateResolver.setSuffix(".xml");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCacheable(true);
		templateResolver.setCharacterEncoding("UTF-8");
		return templateResolver;
	}
}
