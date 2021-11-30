package com.pastley.infrastructure.dto;

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
public class RoleModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;

	public RoleModel(String name) {
		this(0L, name);
	}
	
	public RoleModel(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
