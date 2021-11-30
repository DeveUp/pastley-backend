package com.pastley.infrastructure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pastley.application.service.ProductService;
import com.pastley.domain.Product;

/**
 * @project Pastley-Product.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@RestController
@RequestMapping("/product")
public class ProductRest {

	@Autowired
	ProductService productService;

	@GetMapping(value = { "/find/id/{id}", "/{id}" })
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(productService.findById(id));
	}

	@GetMapping(value = { "/find/name/{name}" })
	public ResponseEntity<?> findByName(@PathVariable("name") String name) {
		return ResponseEntity.status(HttpStatus.OK).body(productService.findByName(name));
	}

	@GetMapping(value = { "", "/all" })
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());
	}
	
	@GetMapping(value = { "/all/find/discount" })
	public ResponseEntity<?> findProductByPromotionAll() {
		return ResponseEntity.status(HttpStatus.OK).body(productService.findProductByPromotionAll());
	}
	
	@GetMapping(value = {"/all/find/statu/{statu}" })
	public ResponseEntity<?> findByStatuAll(@PathVariable("statu") boolean statu) {
		return ResponseEntity.status(HttpStatus.OK).body(productService.findByStatuAll(statu));
	}
	
	@GetMapping(value = {"/all/find/category/{statu}" })
	public ResponseEntity<?> findByIdCategoryAll(@PathVariable("statu") Long idCategory) {
		return ResponseEntity.status(HttpStatus.OK).body(productService.findByIdCategory(idCategory));
	}
	
	@GetMapping(value = "/range/all/find/date/register/{start}/{end}")
	public ResponseEntity<?> findByRangeDateRegister(@PathVariable("start") String start, @PathVariable("end") String end) {
		return ResponseEntity.status(HttpStatus.OK).body(productService.findByRangeDateRegister(start, end));
	}

	@PostMapping()
	public ResponseEntity<?> create(@RequestBody Product product) {
		return ResponseEntity.status(HttpStatus.OK).body(productService.save(product, 1));
	}

	@PutMapping()
	public ResponseEntity<?> update(@RequestBody Product product) {
		return ResponseEntity.status(HttpStatus.OK).body(productService.save(product, 2));
	}

	@PutMapping(value = "/update/statu/{id}")
	public ResponseEntity<?> updateRoleStatu(@PathVariable("id") Long id) {
		Product product = productService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(productService.save(product, 3));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(productService.delete(id));
	}
}
