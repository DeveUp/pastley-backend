package com.pastley.controller;

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

import com.pastley.models.entity.TypePQR;
import com.pastley.models.service.TypePQRService;

/**
 * @project Pastley-Contact.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/serbuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@RestController
@RequestMapping("type-pqr")
public class TypePQRRes{
	
	@Autowired
	TypePQRService typePQRService;

	@GetMapping(value = { "/find/id/{id}", "{id}" })
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(typePQRService.findById(id));
	}

	@GetMapping(value = { "/find/name/{name}" })
	public ResponseEntity<?> findByName(@PathVariable("name") String name) {
		return ResponseEntity.status(HttpStatus.OK).body(typePQRService.findByName(name));
	}

	@GetMapping(value = {"", "/all"})
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(typePQRService.findAll());
	}
	
	@GetMapping(value = "/all/find/statu/{statu}")
	public ResponseEntity<?> findByStatuAll(@PathVariable("statu") Boolean statu) {
		return ResponseEntity.status(HttpStatus.OK).body(typePQRService.findByStatuAll(statu));
	}
	
	@GetMapping(value = "/range/all/find/date/register/{start}/{end}")
	public ResponseEntity<?> findByRangeDateRegister(@PathVariable("start") String start, @PathVariable("end") String end) {
		return ResponseEntity.status(HttpStatus.OK).body(typePQRService.findByRangeDateRegister(start, end));
	}
	
	@GetMapping(value = "/statistic/find/type/{id}")
	public ResponseEntity<?> findByStatisticSale(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(typePQRService.findByStatisticType(id));
	}

	@GetMapping(value = "/statistic/all/find/type")
	public ResponseEntity<?> findByStatisticSaleAll() {
		return ResponseEntity.status(HttpStatus.OK).body(typePQRService.findByStatisticTypeAll());
	}

	@PostMapping()
	public ResponseEntity<?> create(@RequestBody TypePQR type) {
		return ResponseEntity.status(HttpStatus.OK).body(typePQRService.save(type, 1));
	}

	@PutMapping()
	public ResponseEntity<?> update(@RequestBody TypePQR type) {
		return ResponseEntity.status(HttpStatus.OK).body(typePQRService.save(type, 2));
	}
	
	@PutMapping(value = "/update/statu/{id}")
	public ResponseEntity<?> updateStatu(@PathVariable("id") Long id) {
		TypePQR type = typePQRService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(typePQRService.save(type, 3));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(typePQRService.delete(id));
	}
}
