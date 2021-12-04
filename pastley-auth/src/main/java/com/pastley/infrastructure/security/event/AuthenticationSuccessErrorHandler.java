package com.pastley.infrastructure.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.pastley.domain.User;
import com.pastley.infrastructure.feign.UserFeign;

import feign.FeignException;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	private Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);

	@Autowired
	private UserFeign usuarioService;
	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		
		// if(authentication.getName().equalsIgnoreCase("frontendapp")) {
		if(authentication.getDetails() instanceof WebAuthenticationDetails) {
			return;
		}
		
		UserDetails user = (UserDetails) authentication.getPrincipal();
		String mensaje = "Success Login: " + user.getUsername();
		System.out.println(mensaje);
		log.info(mensaje);

		User aux = usuarioService.findByNickname(authentication.getName());
		
		if(aux.getAttempts() > 0) {
			aux.setAttempts(0);
		}
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String mensaje = "Error en el Login: " + exception.getMessage();
		log.error(mensaje);
		try {
			StringBuilder errors = new StringBuilder();
			errors.append(mensaje);
			User aux = usuarioService.findByNickname(authentication.getName());
		} catch (FeignException e) {
			log.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
		}

	}

}
