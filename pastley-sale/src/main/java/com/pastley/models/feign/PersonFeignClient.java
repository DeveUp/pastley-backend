package com.pastley.models.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pastley.models.dto.PersonDTO;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@FeignClient(name = "pastley-user")
@RequestMapping("/person")
public interface PersonFeignClient {

	@GetMapping(value = { "/findByDocument/{document}" })
	public PersonDTO findByDocument(@PathVariable("document") Long document);
}
