package com.pastley;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @project Pastley-Buy.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class PastleyBuyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PastleyBuyApplication.class, args);
	}

}
