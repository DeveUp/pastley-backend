package com.pastley.infrastructure.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pastley.domain.User;

public interface UserFeign {
	
	@GetMapping(value = { "/find/nickname/{nickname}"})
	User findByNickname(@PathVariable("nickname") String nickname);
}
