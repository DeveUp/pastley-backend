package com.pastley.infrastructure.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.pastley.domain.User;
import com.pastley.infrastructure.feign.UserFeign;

@Component
public class InfoAdicionalToken implements TokenEnhancer{

	@Autowired
	private UserFeign userFeign;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info = new HashMap<String, Object>();
		User usuario = userFeign.findByNickname(authentication.getName());
		info.put("nickname", usuario.getNickname());
		info.put("email", usuario.getPerson().getEmail());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}

}
