package rdvmedecins.back.admin.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.RememberMeServices;

import rdvmedecins.security.CustomAccessDeniedHandler;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/*
	 * Dependency injection
	 * =========================================================================
	 */
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	@Autowired
    private RememberMeServices rememberMeServices;
		
	@Autowired
	private Environment env;

	
	/*
	 * Beans
	 * =========================================================================
	 */
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
	
    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
    
	/*
	 * Overrided Config Methods
	 * =========================================================================
	 */
    
	 @Override
	    public void configure(WebSecurity web) throws Exception {
	        web.ignoring()
	            .antMatchers("/static/**/*.{js,html,css}")
	            .antMatchers("/i18n/**")
	            .antMatchers("/webjars/**")
	            .antMatchers("/test/**");
	    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {		
		http
        .csrf()
        	.and()
            //.addFilterAfter(new CsrfCookieGeneratorFilter(), CsrfFilter.class)
        	.exceptionHandling()
        	.accessDeniedHandler(new CustomAccessDeniedHandler())
        	//.authenticationEntryPoint(authenticationEntryPoint)
        	.and()
        .rememberMe()
        	.rememberMeServices(rememberMeServices)
        	// The name of the “check box”
        	.rememberMeParameter("remember-me")
        	.key(env.getProperty("zocdoc.security.rememberme.key"))
        	.and()
        .formLogin()
        	.loginPage("/login")
        	.defaultSuccessUrl("/", false)
        	.permitAll()
        	.and()
        .logout()
        	.logoutUrl("/logout")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID", "CSRF-TOKEN")
			.permitAll()
			.and()
        .headers()
            .frameOptions()
            .disable()
            .and()
        .authorizeRequests()    
        	.antMatchers("/login").permitAll()    
        	.antMatchers("/", "/api/register").permitAll()
            .antMatchers("/api/activate").permitAll()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/admin","/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
			;   
	}
}
