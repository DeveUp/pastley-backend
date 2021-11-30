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

import com.pastley.application.service.ProviderService;
import com.pastley.domain.Provider;

/**
 * @project Pastley-Buy.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@RestController()
@RequestMapping("/provider")
public class ProviderRest {
	
	@Autowired
	ProviderService providerService;
    
	@GetMapping(value = { "/find/id/{id}", "/{id}" })
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(providerService.findById(id));
	}
	
	@GetMapping(value = { "/find/name/{name}" })
	public ResponseEntity<?> findByName(@PathVariable("name") String name) {
		return ResponseEntity.status(HttpStatus.OK).body(providerService.findByName(name));
	}

	@GetMapping(value = { "", "/all" })
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(providerService.findAll());
	}
	
	@GetMapping(value = {"/all/find/statu/{statu}" })
	public ResponseEntity<?> findByStatuAll(@PathVariable("statu") boolean statu) {
		return ResponseEntity.status(HttpStatus.OK).body(providerService.findByStatuAll(statu));
	}
	
	@GetMapping(value = "/range/all/find/date/register/{start}/{end}")
	public ResponseEntity<?> findByRangeDateRegister(@PathVariable("start") String start, @PathVariable("end") String end) {
		return ResponseEntity.status(HttpStatus.OK).body(providerService.findByRangeDateRegister(start, end));
	}

	@PostMapping()
	public ResponseEntity<?> create(@RequestBody Provider provider) {
		return ResponseEntity.status(HttpStatus.OK).body(providerService.save(provider, 1));
	}

	@PutMapping()
	public ResponseEntity<?> update(@RequestBody Provider provider) {
		return ResponseEntity.status(HttpStatus.OK).body(providerService.save(provider, 2));
	}
	
	@PutMapping(value = "/update/statu/{id}")
	public ResponseEntity<?> updateRoleStatu(@PathVariable("id") Long id) {
		Provider provider = providerService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(providerService.save(provider, 3));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(providerService.delete(id));
	}
}
