package com.pastley.models.service;

import java.util.List;

import com.pastley.models.entity.Contact;

/**
 * @project Pastley-Contact.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/serbuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public interface ContactService {
	
	Contact findById(Long id);

	List<Contact> findAll();

	List<Contact> findByUserAll(Long idUser);

	List<Contact> findByTypePqrAll(Long idTypePqr);

	List<Contact> findByStatuAll(boolean statu);

	List<Contact> findByRangeDateRegister(String start, String end);

	Contact save(Contact entity, int type);

	boolean delete(Long id);
}
