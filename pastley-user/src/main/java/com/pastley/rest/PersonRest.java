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

import com.pastley.model.entity.Person;
import com.pastley.model.service.PersonService;

/**
 * @project Pastley-User.
 * @author Leyner Jose Ortega Arias.
 * @Github https://github.com/leynerjoseoa.
 * @contributors serbuitrago.
 * @version 1.0.0.
 */
@RestController
@RequestMapping("person")
public class PersonRest {

	@Autowired
	PersonService personService;
	
	@GetMapping(value = { "/find/id/{id}", "/{id}" })
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(personService.findById(id));
	}
	
	@GetMapping(value = { "/find/document/{document}" })
	public ResponseEntity<?> findByDocument(@PathVariable("document") Long document) {
		return ResponseEntity.status(HttpStatus.OK).body(personService.findByDocument(document));
	}
	
	@GetMapping(value = { "/find/email/{email}" })
	public ResponseEntity<?> findByEmail(@PathVariable("email") String email) {
		return ResponseEntity.status(HttpStatus.OK).body(personService.findByEmail(email));
	}
	
	@GetMapping(value = { "", "/all" })
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(personService.findAll());
	}
	
	@GetMapping(value = {"/all/find/type-document/{idTypeDocument}" })
	public ResponseEntity<?> findByIdTypeDocumentAll(@PathVariable("idTypeDocument") Long idTypeDocument) {
		return ResponseEntity.status(HttpStatus.OK).body(personService.findByIdTypeDocumentAll(idTypeDocument));
	}
	
	@PostMapping()
	public ResponseEntity<?> create(@RequestBody Person person) {
		return ResponseEntity.status(HttpStatus.OK).body(personService.save(person, 1));
	}
	
	@PutMapping()
	public ResponseEntity<?> update(@RequestBody Person person) {
		return ResponseEntity.status(HttpStatus.OK).body(personService.save(person, 2));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(personService.delete(id));
	}
}
