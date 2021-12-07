package com.pastley.models.service;

import java.util.List;

import com.pastley.models.entity.Person;

/**
 * @project Pastley-User.
 * @author Leyner Jose Ortega Arias.
 * @Github https://github.com/leynerjoseoa.
 * @contributors serbuitrago.
 * @version 1.0.0.
 */
public interface PersonService {

	Person findById(Long id);
	
	Person findByPhone(String phone);

	Person findByEmail(String email);

	Person findByDocument(Long document);

	List<Person> findAll();

	List<Person> findByIdTypeDocumentAll(Long idTypeDocument);

	Person save(Person entity, int type);
	
	Person saveOrUpdate(Person entity);

	boolean delete(Long id);
}
