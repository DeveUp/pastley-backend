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

import com.pastley.models.entity.Buy;
import com.pastley.models.service.BuyService;

/**
 * @project Pastley-Buy.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@RestController()
@RequestMapping("/buy")
public class BuyRest {
	
	@Autowired
	BuyService buyService;

	@GetMapping(value = { "/find/id/{id}", "/{id}" })
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(buyService.findById(id));
	}
	
	@GetMapping(value = { "", "/all" })
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(buyService.findAll());
	}
	
	@GetMapping(value = {"/all/find/statu/{statu}" })
	public ResponseEntity<?> findByStatuAll(@PathVariable("statu") boolean statu) {
		return ResponseEntity.status(HttpStatus.OK).body(buyService.findByStatuAll(statu));
	}
	
	@GetMapping(value = "/range/all/find/date/register/{start}/{end}")
	public ResponseEntity<?> findByRangeDateRegister(@PathVariable("start") String start, @PathVariable("end") String end) {
		return ResponseEntity.status(HttpStatus.OK).body(buyService.findByRangeDateRegister(start, end));
	}

	@PostMapping()
	public ResponseEntity<?> create(@RequestBody Buy buy) {
		return ResponseEntity.status(HttpStatus.OK).body(buyService.save(buy, 1));
	}

	@PutMapping()
	public ResponseEntity<?> update(@RequestBody Buy buy) {
		return ResponseEntity.status(HttpStatus.OK).body(buyService.save(buy, 2));
	}
	
	@PutMapping(value = "/update/statu/{id}")
	public ResponseEntity<?> updateRoleStatu(@PathVariable("id") Long id) {
		Buy buy = buyService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(buyService.save(buy, 3));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(buyService.delete(id));
	}
}
