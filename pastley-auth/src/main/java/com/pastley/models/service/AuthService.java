package com.pastley.models.service;

import com.pastley.models.dto.UserDTO;

public interface AuthService {
	
	UserDTO findUserName(String token);
	
	boolean findIsToken(String token);
	
	String login(UserDTO userDTO);

	boolean logout(String token);
}
