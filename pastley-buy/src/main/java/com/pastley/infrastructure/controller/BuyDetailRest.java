package com.pastley.infrastructure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pastley.application.service.BuyDetailService;
import com.pastley.domain.BuyDetail;

/**
 * @project Pastley-Buy.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@RestController()
@RequestMapping("/buy-detail")
public class BuyDetailRest {
	
	@Autowired
	BuyDetailService buyDetailService;
	
	@PostMapping()
	public ResponseEntity<?> create(@RequestBody BuyDetail buyDetail) {
		return ResponseEntity.status(HttpStatus.OK).body(buyDetailService.save(buyDetail));
	}
}
