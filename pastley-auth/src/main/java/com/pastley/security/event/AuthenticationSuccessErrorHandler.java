package com.pastley.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.pastley.models.dto.UserDTO;
import com.pastley.models.service.UserService;
import com.pastley.util.exception.PastleyException;

/**
 * @project Pastley-Auth.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	private Logger LOGGER = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);

	@Autowired
	private UserService userService;

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		if (authentication.getDetails() instanceof WebAuthenticationDetails)
			return;
		UserDetails user = (UserDetails) authentication.getPrincipal();
		String mensaje = "Success Login: " + user.getUsername();
		System.out.println(mensaje);
		LOGGER.info(mensaje);
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String mensaje = "Error en el Login: " + exception.getMessage();
		LOGGER.error(mensaje);
		System.out.println(mensaje);
		try {
			StringBuilder errors = new StringBuilder();
			errors.append(mensaje);
			UserDTO userModel = userService.findByNickname(authentication.getName());
			System.out.println(userModel);
		} catch (PastleyException e) {
			LOGGER.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
		}

	}
}