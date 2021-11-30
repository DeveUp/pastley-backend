package com.pastley.infrastructure.config;

import java.io.Serializable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Configuration
public class RestTemplateConfig implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Bean
	public RestTemplate template() {
		return new RestTemplate();
	}
}
