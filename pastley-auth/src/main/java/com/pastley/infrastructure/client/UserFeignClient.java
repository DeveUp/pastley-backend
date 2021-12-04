package com.pastley.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pastley.domain.UserModel;

@FeignClient(name="pastley-user")
@RequestMapping("/user")
public interface UserFeignClient {

	@GetMapping(value = { "/find/nickname/{nickname}"})
	public UserModel findByNickname(@PathVariable("nickname") String nickname);
}
