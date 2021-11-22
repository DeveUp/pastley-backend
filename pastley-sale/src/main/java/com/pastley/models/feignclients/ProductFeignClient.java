package com.pastley.models.feignclients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pastley.models.model.ProductModel;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors soleimygomez, leynerjoseoa, jhonatanbeltran.
 * @version 1.0.0.
 */
@FeignClient(name = "pastley-product")
@RequestMapping("/product")
public interface ProductFeignClient {
	
	@GetMapping(value = { "/{id}" })
	public ProductModel findById(@PathVariable("id") Long id);
	
	@PutMapping(value = {"update/stock"})
	public ProductModel updateStock(@RequestBody ProductModel product);
}
