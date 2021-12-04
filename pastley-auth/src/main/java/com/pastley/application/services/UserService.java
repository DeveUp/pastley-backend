package com.pastley.application.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pastley.domain.UserModel;
import com.pastley.infrastructure.client.UserFeignClient;

import feign.FeignException;

@Service
public class UserService implements UserDetailsService {

	private Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserFeignClient userFeignClient;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			UserModel userModel = findByNickname(username);
			List<GrantedAuthority> authorities = new ArrayList<>();
			return new User(userModel.getPerson().getEmail(), userModel.getPassword(), authorities);

		} catch (FeignException e) {
			LOGGER.error("[loadUserByUsername(String username) throws UsernameNotFoundException]", 2);
			throw new UsernameNotFoundException("No se ha encontra ningun usuario con ese apodo.");
		}
	}
	
	public UserModel findByNickname(String nickname) {
		return userFeignClient.findByNickname(nickname);
	}
}
