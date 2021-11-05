package com.pastley;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors soleimygomez, leynerjoseoa, jhonatanbeltran.
 * @version 1.0.0.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class PastleyContactApplication {

	public static void main(String[] args) {
		SpringApplication.run(PastleyContactApplication.class, args);
	}
}
