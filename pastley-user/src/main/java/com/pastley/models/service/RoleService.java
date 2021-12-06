package com.pastley.models.service;

import java.util.List;

import com.pastley.models.entity.Role;

/**
 * @project Pastley-User.
 * @author Leyner Jose Ortega Arias.
 * @Github https://github.com/leynerjoseoa.
 * @contributors serbuitrago.
 * @version 1.0.0.
 */
public interface RoleService {

	Role findById(Long id);

	Role findByName(String name);

	List<Role> findAll();

	Role save(Role entity, int type);

	boolean delete(Long id);
}