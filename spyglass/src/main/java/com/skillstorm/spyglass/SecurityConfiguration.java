package com.skillstorm.spyglass;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	//See the following link for more info on how to set up basic authentication:
	//https://www.devglan.com/spring-security/spring-boot-security-rest-basic-authentication

	@Autowired
	private DataSource datasource;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private AuthenticationEntryPointImplementation entryPoint;
	
	@Override
	protected void configure(HttpSecurity http) {
		try {
			http.authorizeRequests().mvcMatchers("/logout/**").permitAll();
			http.csrf().disable().authorizeRequests().anyRequest().authenticated().and()
				.httpBasic().authenticationEntryPoint(entryPoint);
			http.logout().clearAuthentication(true);
			http.cors();//This is required to pick up the corsConfigurer bean.
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(datasource).passwordEncoder(encoder);
	}
}
