package com.pastley.models.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pastley.models.dto.UserDTO;
import com.pastley.models.service.AuthService;
import com.pastley.models.service.UserService;
import com.pastley.security.JWTUtil;
import com.pastley.util.PastleyValidate;
import com.pastley.util.exception.PastleyException;

/**
 * @project Pastley-Auth.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Service
public class AuthServiceImpl implements AuthService {

	private Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JWTUtil jwtUtil;

	@Autowired
	UserService userService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public UserDTO findUserName(String token) {
		if (!findIsToken(token))
			throw new PastleyException("El token no es valido.");
		return userService.findByNickname(jwtUtil.extractUsername(token));
	}

	@Override
	public boolean findIsToken(String token) {
		if (!PastleyValidate.isChain(token))
			throw new PastleyException("El token se ha podido validar, no ha llegado o ha llegado vacio.");
		return jwtUtil.isTokenValid(token);
	}

	@Override
	public String login(UserDTO userDTO) {
		if (userDTO == null)
			throw new PastleyException("No se ha recibido el usuario.");
		if (!PastleyValidate.isChain(userDTO.getNickname()))
			throw new PastleyException("No se ha recibido el apodo del usuario.");
		if (!PastleyValidate.isChain(userDTO.getPassword()))
			throw new PastleyException("No se ha recibido la clave del usuario.");
		UserDetails userDetails = userService.loadUserByUsername(userDTO.getNickname());
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userDTO.getNickname(), userDTO.getPassword()));
			return jwtUtil.generateToken(userDetails);
		} catch (BadCredentialsException e) {
			LOGGER.error("[login(UserDTO userDTO)]", e);
		}
		throw new PastleyException("No se ha logeado.");
	}

	@Override
	public boolean logout(String token) {
		if (!findIsToken(token))
			throw new PastleyException("No hay ningun usuario logeado con ese token.");
		jwtUtil.removeToken(token);
		return true;
	}
}
