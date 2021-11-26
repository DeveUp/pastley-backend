package com.pastley.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pastley.models.service.BuyService;

@RestController()
@RequestMapping("/buy")
public class BuyRest {
	@Autowired
	BuyService buyService;

	@GetMapping(value = { "{id}", "/find/id/{id}" })
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(buyService.findById(id));
	}
}
