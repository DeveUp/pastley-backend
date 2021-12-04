package com.pastley.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pastley.domain.User;
import com.pastley.infrastructure.feign.UserFeign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import feign.FeignException;

public class UserService implements UserDetailsService {

	private Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	UserFeign userFeign;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		try {
			User user = userFeign.findByNickname(username);

			//List<GrantedAuthority> authorities = user.getRoles().stream()
			//		.map(role -> new SimpleGrantedAuthority(role.getNombre()))
		    //			.peek(authority -> log.info("Role: " + authority.getAuthority())).collect(Collectors.toList());

			//log.info("Usuario autenticado: " + username);

			return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true,
					authorities);

		} catch (FeignException e) {
			String error = "Error en el login, no existe el usuario '" + username + "' en el sistema";
			LOGGER.error("[loadUserByUsername(String username) throws UsernameNotFoundException]", e);

			throw new UsernameNotFoundException(error);
		}
	}

}
