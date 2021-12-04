package com.pastley.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserModel {
	private String nickname;
	private String password;
	private PersonModel person;
}
