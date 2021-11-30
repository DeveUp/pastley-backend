package com.pastley.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @project Pastley-User.
 * @author Leyner Jose Ortega Arias.
 * @Github https://github.com/leynerjoseoa.
 * @contributors serbuitrago.
 * @version 1.0.0.
 */
@Configuration
@EnableSwagger2
public class SwaggerUserConfig {
	
	@Bean
	public Docket document() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage(PastleyVariable.PASTLEY_PATH_REST))
				.paths(PathSelectors.any())
				.build();
	}
}
