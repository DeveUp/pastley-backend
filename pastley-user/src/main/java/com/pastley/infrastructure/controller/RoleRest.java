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

import com.pastley.application.service.RoleService;
import com.pastley.domain.Role;

/**
 * @project Pastley-User.
 * @author Leyner Jose Ortega Arias.
 * @Github https://github.com/leynerjoseoa.
 * @contributors serbuitrago.
 * @version 1.0.0.
 */
@RestController
@RequestMapping("role")
public class RoleRest {
	
	@Autowired
	private RoleService roleService;

	@GetMapping(value = { "/find/id/{id}", "/{id}" })
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(roleService.findById(id));
	}
	
	@GetMapping(value = { "/find/name/{name}" })
	public ResponseEntity<?> findByName(@PathVariable("name") String name) {
		return ResponseEntity.status(HttpStatus.OK).body(roleService.findByName(name));
	}

	@GetMapping(value = { "", "/all" })
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(roleService.findAll());
	}

	@PostMapping()
	public ResponseEntity<?> create(@RequestBody Role role) {
		return ResponseEntity.status(HttpStatus.OK).body(roleService.save(role, 1));
	}

	@PutMapping()
	public ResponseEntity<?> update(@RequestBody Role role) {
		return ResponseEntity.status(HttpStatus.OK).body(roleService.save(role, 2));
	}
	
	@PutMapping(value = "/update/statu/{id}")
	public ResponseEntity<?> updateRoleStatu(@PathVariable("id") Long id) {
		Role role = roleService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(roleService.save(role, 3));
	}
	
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(roleService.delete(id));
	}
}
