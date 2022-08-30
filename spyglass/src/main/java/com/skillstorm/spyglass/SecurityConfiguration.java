package com.skillstorm.spyglass;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource datasource;
	
//	@Autowired
//	private PasswordEncoder encoder;
	
	@Override
	protected void configure(HttpSecurity http) {
		try {
			http.formLogin();
			http.csrf().disable();
//			http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
//			http.authorizeHttpRequests().mvcMatchers("/logout/**").permitAll();
//			http.logout().deleteCookies("JSESSIONID").invalidateHttpSession(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Autowired
//	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		super.configure(auth);
//		auth.jdbcAuthentication().dataSource(datasource).passwordEncoder(encoder);
//	}
}
