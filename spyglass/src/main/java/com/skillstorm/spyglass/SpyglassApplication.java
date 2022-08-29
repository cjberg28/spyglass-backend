package com.skillstorm.spyglass;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class SpyglassApplication extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource datasource;
	
	public static void main(String[] args) {
		SpringApplication.run(SpyglassApplication.class, args);
	}

	@Override
	protected void configure(HttpSecurity http) {
		//Place any security config in here
	}
}
