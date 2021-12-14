package com.pastley.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pastley.models.dto.UserDTO;
import com.pastley.models.service.AuthService;

/**
 * @project Pastley-Auth.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@RestController
@RequestMapping("/auth")
public class AuthRest {
	
	@Autowired
	AuthService authService;
	
	@GetMapping(value = { "/find/is/token/{token}"})
	public ResponseEntity<?> findIsToken(@PathVariable("token") String token) {
		return ResponseEntity.status(HttpStatus.OK).body(authService.findIsToken(token));
	}
	
	@GetMapping(value = { "/find/user/token/{token}"})
	public ResponseEntity<?> findUserName(@PathVariable("token") String token) {
		return ResponseEntity.status(HttpStatus.OK).body(authService.findUserName(token));
	}
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO user){
		return ResponseEntity.status(HttpStatus.OK).body(authService.login(user));
	}
	
	@PostMapping("/logout/{token}")
    public ResponseEntity<?> logout(@PathVariable("token") String token){
		return ResponseEntity.status(HttpStatus.OK).body(authService.logout(token));
	}
}
