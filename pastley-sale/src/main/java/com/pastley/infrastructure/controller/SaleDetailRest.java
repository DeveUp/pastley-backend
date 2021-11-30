package com.pastley.infrastructure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pastley.application.service.SaleDetailService;
import com.pastley.domain.SaleDetail;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors soleimygomez, leynerjoseoa, jhonatanbeltran.
 * @version 1.0.0.
 */
@RestController
@RequestMapping("/saleDetail")
public class SaleDetailRest {
	
	@Autowired
	SaleDetailService saleDetailService;

	///////////////////////////////////////////////////////
	// Method - Get
	///////////////////////////////////////////////////////
	/**
	 * Method that allows consulting a sale detail by its id.
	 * 
	 * @param id, Represents the identifier of the sale detail.
	 * @return The generated response.
	 */
	@GetMapping(value = { "/find/id/{id}", "/{id}" })
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(saleDetailService.findById(id));
	}
	
	/**
	 * Method that allows to obtain all the sales details.
	 * 
	 * @return The generated response.
	 */
	@GetMapping(value = {"", "/all"})
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(saleDetailService.findAll());
	}
	
	/**
	 * Method that allows knowing the sale details of a sale made.
	 * @param idSale, Represents the identifier of the sale.
	 * @return The generated response.
	 */
	@GetMapping(value = "/find/sale/{idSale}")
	public ResponseEntity<?> findBySale(@PathVariable("sale") Long idSale){
		return ResponseEntity.status(HttpStatus.OK).body(saleDetailService.findBySale(idSale));
	}
	
	///////////////////////////////////////////////////////
	// Method - Put
	///////////////////////////////////////////////////////
	/**
	 * Method that allows updating a sale detail.
	 * 
	 * @param saleDetail, Represents the sale detail to update.
	 * @return The generated response.
	 */
	@PutMapping(value = "")
	public ResponseEntity<?> update(@RequestBody SaleDetail saleDetail) {
		return ResponseEntity.status(HttpStatus.OK).body(saleDetailService.save(saleDetail));
	}
	
	///////////////////////////////////////////////////////
	// Method - Delete
	///////////////////////////////////////////////////////
	/**
	 * Method that allows you to delete a sale detail by means of its id.
	 * @param id, Represents the identifier of the sale detail to be deleted.
	 * @return The generated response.
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(saleDetailService.delete(id));
	}
}
