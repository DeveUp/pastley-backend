package com.pastley.rest;

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

import com.pastley.entity.Cart;
import com.pastley.service.CartService;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors soleimygomez, leynerjoseoa, jhonatanbeltran.
 * @version 1.0.0.
 */
@RestController
@RequestMapping("/cart")
public class CartRest implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private CartService cartService;

	///////////////////////////////////////////////////////
	// Method - Get
	///////////////////////////////////////////////////////
	/**
	 * Method that allows you to check the cart for its id.
	 * 
	 * @param id, Represents the identifier of the cart.
	 * @return The generated response.
	 */
	@GetMapping(value = { "/find/id/{id}", "{id}" })
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(cartService.findById(id));
	}
	
	/**
	 * Method that allows consulting a product in the cart by the customer id and
	 * the product id.
	 * 
	 * @param idCustomer, Represents the customer id.
	 * @param statu,      Represents the status of the product.
	 * @param idProduct,  Represents the product id.
	 * @return The generated response.
	 */
	@GetMapping(value = { "/find/customer/{idCustomer}/product/{idProduct}/statu/{statu}"})
	public ResponseEntity<?> findByCustomerAndProductAndStatu(Boolean statu, Long idCustomer, Long idProduct){
		return ResponseEntity.status(HttpStatus.OK).body(cartService.findByCustomerAndProductAndStatu(statu, idCustomer, idProduct));
	}
	
	/**
	 * Method that allows you to consult all the products in the cart.
	 * @return The generated response.
	 */
	@GetMapping(value = {"", "/all"})
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(cartService.findAll());
	}
	
	/**
	 * Method that allows you to consult all the carts by their status.
	 * @param statu, Represents the status of the product in cart.
	 * @return The generated response.
	 */
	@GetMapping(value = "/all/find/statu/{statu}")
	public ResponseEntity<?> findByStatuAll(@PathVariable("statu") Boolean statu) {
		return ResponseEntity.status(HttpStatus.OK).body(cartService.findByStatuAll(statu));
	}
	
	/**
	 * Method that allows consulting all the products of a client.
	 * @param idCustomer, Represents the customer id.
	 * @return The generated response.
	 */
	@GetMapping(value = { "/all/find/customer/{idCustomer}"})
	public ResponseEntity<?> findProductsByCustomer(@PathVariable("idCustomer") Long idCustomer) {
		return ResponseEntity.status(HttpStatus.OK).body(cartService.findByCustomer(idCustomer));
	}
	
	/**
	 * Method that allows consulting the different carts with the same product.
	 * @param idCustomer, Represents the customer id.
	 * @param idProduct,  Represents the product id.
	 * @return The generated response.
	 */
	@GetMapping(value = { "/all/find/customer/{idCustomer}/product/{idProduct}"})
	public ResponseEntity<?> findByCustomerAndProduct(@PathVariable("idCustomer") Long idCustomer, @PathVariable("idProduct") Long idProduct){
		return ResponseEntity.status(HttpStatus.OK).body(cartService.findByCustomerAndProduct(idCustomer, idProduct));
	}
	
	/**
	 * Method that allows to consult all the products of a client and by their status.
	 * @param id, Represents the customer id.
	 * @param statu, Represents the status of the product
	 * @return The generated response.
	 */
	@GetMapping(value = { "/all/find/customer/{idCustomer}/statu/{statu}"})
	public ResponseEntity<?> findProductsByCustomer(@PathVariable("idCustomer") Long idCustomer, @PathVariable("statu") Boolean statu) {
		return ResponseEntity.status(HttpStatus.OK).body(cartService.findByCustomerAndStatus(idCustomer, statu));
	}
	
	/**
	 * Method that allows you to filter the products in the cart that are registered between a range of dates.
	 * @param start, Represents the start date.
	 * @param end, Represents the end date.
	 * @return The generated response.
	 */
	@GetMapping(value = "/range/all/find/date/register/{start}/{end}")
	public ResponseEntity<?> findByRangeDateRegister(@PathVariable("start") String start, @PathVariable("end") String end) {
		return ResponseEntity.status(HttpStatus.OK).body(cartService.findByRangeDateRegister(start, end));
	}
	
	/**
	 * Method that allows you to filter the products in the cart that are registered between a range of dates and the customer's id.
	 * @param idCustomer, Represents the customer id.
	 * @param start, Represents the start date.
	 * @param end, Represents the end date.
	 * @return The generated response.
	 */
	@GetMapping(value = "/range/all/find/customer/{idCustomer}/date/register/{start}/{end}")
	public ResponseEntity<?> findByRangeDateRegister(@PathVariable("idCustomer") Long idCustomer, @PathVariable("start") String start, @PathVariable("end") String end) {
		return ResponseEntity.status(HttpStatus.OK).body(cartService.findByRangeDateRegisterAndCustomer(idCustomer, start, end));
	}
	
	///////////////////////////////////////////////////////
	// Method - Post
	///////////////////////////////////////////////////////
	/**
	 * Method that allows you to register a product in the cart.
	 * @param cart, Represents the cart.
	 * @return The generated response.
	 */
	@PostMapping(value = "")
	public ResponseEntity<?> create(@RequestBody Cart cart) {
		return ResponseEntity.status(HttpStatus.OK).body(cartService.save(cart, (byte) 1));
	}
	
	///////////////////////////////////////////////////////
	// Method - Put
	///////////////////////////////////////////////////////
	/**
	 * Method that allows updating the information of a product in the cart.
	 * 
	 * @param cart, Represents the cart.
	 * @return The generated response.
	 */
	@PutMapping(value = "")
	public ResponseEntity<?> update(@RequestBody Cart cart) {
		return ResponseEntity.status(HttpStatus.OK).body(cartService.save(cart, (byte) 2));
	}
	
	/**
	 * Method that allows updating the status of a cart product
	 * 
	 * @param id, Represents the identifier of the cart.
	 * @return The generated response.
	 */
	@PutMapping(value = "/update/{id}/statu")
	public ResponseEntity<?> updateStatu(@PathVariable("id") Long id) {
		Cart cart = cartService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(cartService.save(cart, (byte) 3));
	}
	
	///////////////////////////////////////////////////////
	// Method - Delete
	///////////////////////////////////////////////////////
	/**
	 * Method that allows you to delete a product with the cart.
	 * @param id, Represents the identifier of the cart.
	 * @return The generated response.
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(cartService.delete(id));
	}	
}
