package com.pastley.models.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pastley.models.dto.UserDTO;

@FeignClient(name="pastley-user")
@RequestMapping("/user")
public interface UserFeignClient {

	@GetMapping(value = { "/find/nickname/{nickname}"})
	public UserDTO findByNickname(@PathVariable("nickname") String nickname);
}
