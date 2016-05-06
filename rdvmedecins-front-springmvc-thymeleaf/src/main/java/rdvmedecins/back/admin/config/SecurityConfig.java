package rdvmedecins.back.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import rdvmedecins.security.AppUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AppUserDetailsService appUserDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder registry) throws Exception {
		// l'authentification est faite par le bean [appUserDetailsService]
		// le mot de passe est crypté par l'algorithme de hachage BCrypt
		registry.userDetailsService(appUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
		
		// registry.inMemoryAuthentication().withUser("user").password("password").roles("USER");
		// registry.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");

	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {		
		http
        .csrf().disable()
        .authorizeRequests()
            .antMatchers("/", "/register", "/static/**", "/webjars/**").permitAll()
            .antMatchers("/admin","/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
        .formLogin()
        	.loginPage("/login")
        	.defaultSuccessUrl("/", false)
        	.permitAll()
        	.and()
        .logout()
			.invalidateHttpSession(true)
			.permitAll();   
	}
}
