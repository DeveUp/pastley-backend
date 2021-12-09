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

	User findByDocumentPerson(Long documentPerson);
	
	User findByNicknameAndIdRol(String nickname, Long idRole);

	List<User> findAll();

	List<User> findByStatuAll(boolean statu);

	List<User> findByIdRole(Long idRole);

	User save(User entity, Long idRole, int type);
	
	User saveAndPersonSaveOrUpdate(User entity);

	boolean delete(Long id);
}