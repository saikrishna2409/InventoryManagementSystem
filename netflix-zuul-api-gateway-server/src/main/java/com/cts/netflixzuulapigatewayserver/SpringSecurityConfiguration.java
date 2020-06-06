package com.cts.netflixzuulapigatewayserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;





@Configuration
public class SpringSecurityConfiguration  extends  WebSecurityConfigurerAdapter {
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		UserBuilder users = User.withDefaultPasswordEncoder();
		
		auth.inMemoryAuthentication()
		.withUser(users.username("devi").password("123").roles("USER"));
	}
	
	
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
		 http.cors().and().csrf().disable();
	        http.authorizeRequests()
	        .antMatchers("/productportal/productdetailsservice/productDetailsService/login").permitAll()
			.antMatchers("/productportal/product-details-service/**").access("hasRole('USER')")

	                .and().httpBasic();
	    }
	
	 @Bean
		public CorsFilter corsFilter() {
			UrlBasedCorsConfigurationSource source= new UrlBasedCorsConfigurationSource();
			CorsConfiguration config =new CorsConfiguration();
			config.setAllowCredentials(true);
			config.addAllowedOrigin("*");
			config.addAllowedHeader("*");
			config.addAllowedMethod("OPTIONS");
			config.addAllowedMethod("GET");
			config.addAllowedMethod("POST");
			config.addAllowedMethod("PUT");
			config.addAllowedMethod("DELETE");
			source.registerCorsConfiguration("/**", config);
			return new CorsFilter(source);
		}




}
