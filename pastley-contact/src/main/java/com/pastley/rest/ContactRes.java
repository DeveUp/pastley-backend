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

import com.pastley.models.entity.Contact;
import com.pastley.models.service.ContactService;

/**
 * @project Pastley-Contact.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/serbuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@RestController
@RequestMapping("contact")
public class ContactRes implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	ContactService contactService;
	
	@GetMapping(value = { "/find/id/{id}", "{id}" })
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(contactService.findById(id));
	}
	
	@GetMapping(value = {"", "/all"})
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(contactService.findAll());
	}
	
	@GetMapping(value = {"", "/all/find/user/{idUser}"})
	public ResponseEntity<?> findByIdUserAll(@PathVariable("idUser") Long idUser) {
		return ResponseEntity.status(HttpStatus.OK).body(contactService.findByUserAll(idUser));
	}
	
	@GetMapping(value = {"", "/all/find/pqr/{idPqr}"})
	public ResponseEntity<?> findByIdPqrAll(@PathVariable("idPqr") Long idPqr) {
		return ResponseEntity.status(HttpStatus.OK).body(contactService.findByTypePqrAll(idPqr));
	}
	
	@GetMapping(value = "/all/find/statu/{statu}")
	public ResponseEntity<?> findByStatuAll(@PathVariable("statu") Boolean statu) {
		return ResponseEntity.status(HttpStatus.OK).body(contactService.findByStatuAll(statu));
	}
	
	@GetMapping(value = "/range/all/find/date/register/{start}/{end}")
	public ResponseEntity<?> findByRangeDateRegister(@PathVariable("start") String start, @PathVariable("end") String end) {
		return ResponseEntity.status(HttpStatus.OK).body(contactService.findByRangeDateRegister(start, end));
	}
	
	@PostMapping()
	public ResponseEntity<?> create(@RequestBody Contact contact) {
		return ResponseEntity.status(HttpStatus.OK).body(contactService.save(contact, 1));
	}

	@PutMapping()
	public ResponseEntity<?> update(@RequestBody Contact contact) {
		return ResponseEntity.status(HttpStatus.OK).body(contactService.save(contact, 2));
	}
	
	@PutMapping(value = "/update/statu/{id}")
	public ResponseEntity<?> updateStatu(@PathVariable("id") Long id) {
		Contact contact = contactService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(contactService.save(contact, 3));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(contactService.delete(id));
	}	
}
