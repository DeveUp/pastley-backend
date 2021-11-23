package com.pastley.rest;


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

import com.pastley.models.entity.MethodPay;
import com.pastley.models.service.MethodPayService;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@RestController
@RequestMapping("/method")
public class MethodPayRest {

	@Autowired
	private MethodPayService methodPayService;

	/**
	 * Method that allows consulting a payment method by its id.
	 * 
	 * @param id, Represents the identifier of the payment method.
	 * @return The generated response.
	 */
	@GetMapping(value = { "/find/id/{id}", "/{id}" })
	public ResponseEntity<MethodPay> findById(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(methodPayService.findById(id));
	}

	/**
	 * Method that allows consulting a payment method by its name.
	 * 
	 * @param name, Represents the name of the payment method.
	 * @return The generated response.
	 */
	@GetMapping(value = { "/find/name/{name}" })
	public ResponseEntity<?> findByName(@PathVariable("name") String name) {
		return ResponseEntity.status(HttpStatus.OK).body(methodPayService.findByName(name));
	}

	/**
	 * Method that allows you to obtain all payment methods.
	 * 
	 * @return The generated response.
	 */
	@GetMapping(value = {"", "/all"})
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(methodPayService.findAll());
	}

	/**
	 * Method that allows you to obtain all payment methods through your state.
	 * 
	 * @return The generated response.
	 */
	@GetMapping(value = "/all/find/statu/{statu}")
	public ResponseEntity<?> findByStatuAll(@PathVariable("statu") Boolean statu) {
		return ResponseEntity.status(HttpStatus.OK).body(methodPayService.findByStatuAll(statu));
	}

	/**
	 * Method that allows filtering the payment methods that are registered between
	 * a date range.
	 * 
	 * @param start, Represents the start date.
	 * @param end,   Represents the end date.
	 * @return The generated response.
	 */
	@GetMapping(value = "/range/all/find/date/register/{start}/{end}")
	public ResponseEntity<?> findByRangeDateRegister(@PathVariable("start") String start, @PathVariable("end") String end) {
		return ResponseEntity.status(HttpStatus.OK).body(methodPayService.findByRangeDateRegister(start, end));
	}

	/**
	 * Method that allows to know the amount of sale made by a payment method.
	 * 
	 * @param id, Represents the identifier of the payment method.
	 * @return The generated response.
	 */
	@GetMapping(value = "/statistic/find/sale/{id}")
	public ResponseEntity<?> findByStatisticSale(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(methodPayService.findByStatisticSale(id));
	}

	/**
	 * Method that allows to know the amount of sales made by a payment method.
	 * 
	 * @return The generated response.
	 */
	@GetMapping(value = "/statistic/all/find/sale")
	public ResponseEntity<?> findByStatisticSaleAll() {
		return ResponseEntity.status(HttpStatus.OK).body(methodPayService.findByStatisticSaleAll());
	}

	/**
	 * Method that allows you to register a payment method.
	 * 
	 * @param method, Represents the payment method to register.
	 * @return The generated response.
	 */
	@PostMapping()
	public ResponseEntity<?> create(@RequestBody MethodPay method) {
		return ResponseEntity.status(HttpStatus.OK).body(methodPayService.save(method, 1));
	}

	/**
	 * Method that allows updating a payment method.
	 * 
	 * @param method, Represents the payment method to update.
	 * @return The generated response.
	 */
	@PutMapping()
	public ResponseEntity<?> update(@RequestBody MethodPay method) {
		return ResponseEntity.status(HttpStatus.OK).body(methodPayService.save(method, 2));
	}

	/**
	 * Method that allows changing the status of a payment method.
	 * 
	 * @param id, Represents the identifier of the payment method.
	 * @return The generated response.
	 */
	@PutMapping(value = "/update/{id}/statu")
	public ResponseEntity<?> updateStatu(@PathVariable("id") Long id) {
		MethodPay method = methodPayService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(methodPayService.save(method, 3));
	}

	/**
	 * Method that allows you to delete a payment method by means of its id.
	 * 
	 * @param id, Represents the identifier of the payment method to be deleted.
	 * @return The generated response.
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(methodPayService.delete(id));
	}
}
