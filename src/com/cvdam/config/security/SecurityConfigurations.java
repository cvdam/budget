package com.cvdam.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	//Authentications
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	//Authorizations
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET,"/").permitAll()
		.antMatchers(HttpMethod.GET,"/expenses").permitAll()
		.antMatchers(HttpMethod.GET,"expenses/*").permitAll()
		.anyRequest().authenticated()
		.and().formLogin();
	}
	
	//Statics resources (javascript, css, images, etc)
	@Override
	public void configure(WebSecurity web) throws Exception {

	}
	
	//Generate hash password for test
	/*public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("123456"));
	}*/
}
