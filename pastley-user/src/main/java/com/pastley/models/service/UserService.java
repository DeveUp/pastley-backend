package com.pastley.models.service;

import java.util.List;

import com.pastley.models.entity.User;

/**
 * @project Pastley-User.
 * @author Leyner Jose Ortega Arias.
 * @Github https://github.com/leynerjoseoa.
 * @contributors serbuitrago.
 * @version 1.0.0.
 */
public interface UserService {

	User findById(Long id);

	User findByNickName(String nickname);

	User findByIdAndIdRol(Long id, Long idRole);

	User findByDocumentPerson(Long documentPerson);

	List<User> findAll();

	List<User> findByStatuAll(boolean statu);

	List<User> findByIdRole(Long idRole);

	User save(User entity, Long idRole, int type);

	boolean delete(Long id);
}