package com.pastley.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
	
	private String nickname;
	private Person person;
	private String password;
	
	private int attempts;
	
	
}
