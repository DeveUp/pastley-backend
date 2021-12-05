package com.pastley.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @project Pastley-Gateway.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@EnableWebFluxSecurity
public class SpringSecurityConfig {
	
	@Autowired
	JwtAuthenticationFilter authenticationFilter;

	@Bean
	public SecurityWebFilterChain configure(ServerHttpSecurity http) {
		return http.authorizeExchange()
				.pathMatchers("/security/login/**").permitAll()
				.pathMatchers(HttpMethod.GET, 
						"/product",
						"/product/{id}",
						"/category",
						"/company/{id}"
				).permitAll()
				.anyExchange().authenticated()
				.and().addFilterAt(
						authenticationFilter, 
						SecurityWebFiltersOrder.AUTHENTICATION
				)
				.csrf().disable()
				.build();
	}
}
