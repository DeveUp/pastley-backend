package com.pastley.models.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pastley.models.dto.ProductDTO;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@FeignClient(name = "pastley-product")
@RequestMapping("/product")
public interface ProductFeignClient {
	
	@GetMapping(value = { "/{id}" })
	public ProductDTO findById(@PathVariable("id") Long id);
	
	@PutMapping(value = {"/update/stock/{id}/{stock}"})
	public ProductDTO updateStock(@PathVariable("id") Long id, @PathVariable("stock") int stock);
}
