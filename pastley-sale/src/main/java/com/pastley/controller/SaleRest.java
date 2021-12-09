package com.pastley.controller;

import java.io.Serializable;

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

import com.pastley.models.entity.Sale;
import com.pastley.models.service.SaleService;
import com.pastley.util.PastleyVariable;
import com.pastley.util.exception.PastleyExceptionModel;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@RestController
@RequestMapping("/sale")
public class SaleRest implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SaleService saleService;

	/**
	 * Method that allows you to consult a s  ale by its id.
	 * 
	 * @param id, Represents the identifier of the sale.
	 * @return The generated response.
	 */
	@GetMapping(value = { "/find/id/{id}", "{id}" })
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(saleService.findById(id));
	}
	
	/**
	 * Method that allows all sales to be consulted.
	 * @return The generated response.
	 */
	@GetMapping(value = {"", "/all"})
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(saleService.findAll());
	}
	
	/**
	 * Method that allows you to check sales by their status.
	 * @param statu, Represents the state.
	 * @return The generated response.
	 */
	@GetMapping(value = "/all/find/statu/{statu}")
	public ResponseEntity<?> findByStatuAll(@PathVariable("statu") Boolean statu) {
		return ResponseEntity.status(HttpStatus.OK).body(saleService.findByStatuAll(statu));
	}
	
	/**
	 * Method that allows knowing all the sales of a user.
	 * @param idCustomer, Represents the id of the user
	 * @return The generated response.
	 */
	@GetMapping(value = {"all/find/customer/{idCustomer}"})
	public ResponseEntity<?> findByIdCustomerAll(@PathVariable("idCustomer") Long idCustomer){
		return ResponseEntity.status(HttpStatus.OK).body(saleService.findByIdCustomerAll(idCustomer));
	}
	
	/**
	 * Method that allows to know the sales by a payment method.
	 * @param idMethodPay, Represents the id of the payment method.
	 * @return The generated response.
	 */
	@GetMapping(value = {"all/find/method/{idMethodPay}"})
	public ResponseEntity<?> findByIdMethodPayAll(@PathVariable("idMethodPay") Long idMethodPay){
		return ResponseEntity.status(HttpStatus.OK).body(saleService.findByIdMethodPayAll(idMethodPay));
	}
	
	/**
	 * Method that allows knowing the name of the current month.
	 * @return The generated response.
	 */
	@GetMapping(value = "all/find/date/register/current")
	public ResponseEntity<?> findByMonthAndYearCurrent(){
		return ResponseEntity.status(HttpStatus.OK).body(saleService.findByMonthAndYearCurrent());
	}
	
	/**
	 * Method that allows to consult all the sales of a month and year indicated.
	 * @param month, Represents the month.
	 * @param year, Represents the year.
	 * @return The generated response.
	 */
	@GetMapping(value = "all/find/date/register/{year}/{month}")
	public ResponseEntity<?> findByMonthAndYear(@PathVariable("year") int year, @PathVariable("month") String month){
		return ResponseEntity.status(HttpStatus.OK).body(saleService.findByMonthAndYear(month, year));
	}
	
	/**
	 * Method that allows consulting the sales that are in a date range.
	 * @param start, Represents the start date.
	 * @param end,   Represents the end date.
	 * @return The generated response.
	 */
	@GetMapping(value = "/range/all/find/date/register/{start}/{end}")
	public ResponseEntity<?> findByRangeDateRegister(@PathVariable("start") String start, @PathVariable("end") String end) {
		return ResponseEntity.status(HttpStatus.OK).body(saleService.findByRangeDateRegister(start, end));
	}


	/**
	 * Method that allows you to register a sale.
	 * @param sale, Represents the sale.
	 * @return The generated response.
	 */
	@CircuitBreaker(name = PastleyVariable.PASTLEY_CIRCUIT_BREAKER_INSTANCES_A, fallbackMethod = PastleyVariable.PASTLEY_CIRCUIT_BREAKER_FALLBACK_METHOD)
	@PostMapping()
	public ResponseEntity<?> create(@RequestBody Sale sale) {
		return ResponseEntity.status(HttpStatus.OK).body(saleService.save(sale, 1));
	}
	
	/**
	 * Method that allows updating the information of a sale.
	 * 
	 * @param sale, Represents the sale.
	 * @return The generated response.
	 */
	@CircuitBreaker(name = PastleyVariable.PASTLEY_CIRCUIT_BREAKER_INSTANCES_A, fallbackMethod = PastleyVariable.PASTLEY_CIRCUIT_BREAKER_FALLBACK_METHOD)
	@PutMapping()
	public ResponseEntity<?> update(@RequestBody Sale sale) {
		return ResponseEntity.status(HttpStatus.OK).body(saleService.save(sale, 2));
	}
	
	/**
	 * Method that allows updating the status of a sale.
	 * 
	 * @param id, Represents the identifier of the sale.
	 * @return The generated response.
	 */
	@CircuitBreaker(name = PastleyVariable.PASTLEY_CIRCUIT_BREAKER_INSTANCES_A, fallbackMethod = PastleyVariable.PASTLEY_CIRCUIT_BREAKER_FALLBACK_METHOD)
	@PutMapping(value = "/update/statu")
	public ResponseEntity<?> updateStatu(@RequestBody Long id) {
		Sale sale= saleService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(saleService.save(sale, 3));
	}
	
	/**
	 * Method that allows you to delete a sale.
	 * @param id, Represents the identifier of the sale.
	 * @return The generated response.
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(saleService.delete(id));
	}
	
	public ResponseEntity<?> fallBack(Exception e){
		return new ResponseEntity<>(PastleyExceptionModel.builder(e, HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
