package com.pastley.models.service;

import java.util.List;

import com.pastley.models.entity.ContactResponse;

/**
 * @project Pastley-Contact.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/serbuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public interface ContactResponseService {

	ContactResponse findById(Long id);
	
	List<ContactResponse> findAll();

	List<ContactResponse> findByContactAll(Long idContact);

	List<ContactResponse> findByRangeDateRegister(String start, String end);

	ContactResponse save(ContactResponse entity);

	boolean delete(Long id);
}
