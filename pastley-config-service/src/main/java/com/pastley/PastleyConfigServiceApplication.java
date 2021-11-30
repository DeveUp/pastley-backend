package com.pastley;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors soleimygomez, leynerjoseoa, jhonatanbeltran.
 * @version 1.0.0.
 */
@SpringBootApplication
@EnableConfigServer
public class PastleyConfigServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PastleyConfigServiceApplication.class, args);
	}

}
