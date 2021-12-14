package com.pastley.models.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.pastley.models.client.UserFeignClient;
import com.pastley.models.dto.UserDTO;
import com.pastley.models.service.UserService;
import com.pastley.util.exception.PastleyException;

import feign.FeignException;

/**
 * @project Pastley-Auth.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	private Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserFeignClient userFeignClient;

	@Override
	public UserDetails loadUserByUsername(String username){
		try {
			UserDTO userModel = findByNickname(username);
			List<GrantedAuthority> authorities = new ArrayList<>();
			return new User(userModel.getNickname(), userModel.getPassword(), authorities);
		} catch (FeignException e) {
			LOGGER.error("[loadUserByUsername(String username) throws UsernameNotFoundException]", 2);
			throw new PastleyException("No se ha encontra ningun usuario con ese apodo "+username+".");
		}
	}

	@Override
	public UserDTO findByNickname(String nickname) {
		return userFeignClient.findByNickname(nickname);
	}
}
