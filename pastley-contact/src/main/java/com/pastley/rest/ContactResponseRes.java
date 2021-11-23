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

import com.pastley.models.entity.ContactResponse;
import com.pastley.models.service.ContactResponseService;

/**
 * @project Pastley-Contact.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/serbuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@RestController
@RequestMapping("contactResponse")
public class ContactResponseRes implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	ContactResponseService contactResponseService;
	
	@GetMapping(value = { "/find/id/{id}", "{id}" })
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(contactResponseService.findById(id));
	}
	
	@GetMapping(value = {"", "/all"})
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(contactResponseService.findAll());
	}
	
	@GetMapping(value = {"/all/find/contact/{idContact}"})
	public ResponseEntity<?> findByIdContactAll(@PathVariable("idContact") Long idContact) {
		return ResponseEntity.status(HttpStatus.OK).body(contactResponseService.findByContactAll(idContact));
	}
	
	@GetMapping(value = "/range/all/find/date/register/{start}/{end}")
	public ResponseEntity<?> findByRangeDateRegister(@PathVariable("start") String start, @PathVariable("end") String end) {
		return ResponseEntity.status(HttpStatus.OK).body(contactResponseService.findByRangeDateRegister(start, end));
	}
	
	@PostMapping()
	public ResponseEntity<?> create(@RequestBody ContactResponse contactResponse) {
		return ResponseEntity.status(HttpStatus.OK).body(contactResponseService.save(contactResponse));
	}

	@PutMapping()
	public ResponseEntity<?> update(@RequestBody ContactResponse contactResponse) {
		return ResponseEntity.status(HttpStatus.OK).body(contactResponseService.save(contactResponse));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(contactResponseService.delete(id));
	}
}
