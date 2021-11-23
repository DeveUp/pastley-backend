package com.pastley.models.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Data
@NoArgsConstructor
public class UserModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long points;
	private String ip;
	private boolean statu;
	private boolean session;
	private PersonModel person;
	private RoleModel role;
	
	public UserModel(String ip, boolean statu, boolean session, PersonModel person, RoleModel role) {
		this(0L, 0L, ip, statu, session, person, role);
	}

	public UserModel(Long id, Long points, String ip, boolean statu, boolean session, PersonModel person,
			RoleModel role) {
		this.id = id;
		this.points = points;
		this.ip = ip;
		this.statu = statu;
		this.session = session;
		this.person = person;
		this.role = role;
	}
}
