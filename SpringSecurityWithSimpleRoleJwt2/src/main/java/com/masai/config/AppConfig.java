package com.masai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class AppConfig {

	@Bean
	public SecurityFilterChain springSecurityConfiguration(HttpSecurity http) throws Exception {

		http
		.cors().and() // Enable CORS
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.csrf().disable()
		.authorizeHttpRequests()
		.requestMatchers(HttpMethod.POST, "/customers").permitAll()
		.requestMatchers(HttpMethod.GET,"/items/search").permitAll()
		.requestMatchers("/swagger-ui*/**","/v3/api-docs/**").permitAll()
		.requestMatchers(HttpMethod.GET, "/customers").hasRole("ADMIN")
		.requestMatchers(HttpMethod.POST,"/items").hasRole("ADMIN")
		.requestMatchers(HttpMethod.GET,"/items").hasRole("ADMIN")
		.requestMatchers(HttpMethod.GET,"/items/{id}").hasRole("ADMIN")
		.requestMatchers(HttpMethod.PUT,"//items/{id}").hasRole("ADMIN")
		.requestMatchers(HttpMethod.DELETE,"/items/{id}").hasRole("ADMIN")
		.requestMatchers(HttpMethod.GET, "/customers/**").hasAnyRole("ADMIN","USER")	
		.anyRequest().authenticated().and()
		.addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
		.addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
		.formLogin()
		.and()
		.httpBasic();

		return http.build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();

	}
	

}
