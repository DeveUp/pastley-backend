package com.pastley;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @project Pastley-Product.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@SpringBootApplication
@EnableEurekaClient
public class PastleyProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PastleyProductsApplication.class, args);
	}

}
