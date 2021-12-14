package com.pastley.models.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.pastley.models.dto.UserDTO;

/**
 * @project Pastley-Auth.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public interface UserService {

	UserDetails loadUserByUsername(String username);

	UserDTO findByNickname(String nickname);
}
