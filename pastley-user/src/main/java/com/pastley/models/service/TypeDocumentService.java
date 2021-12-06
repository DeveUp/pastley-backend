package com.pastley.models.service;

import java.util.List;

import com.pastley.models.entity.TypeDocument;

/**
 * @project Pastley-User.
 * @author Leyner Jose Ortega Arias.
 * @Github https://github.com/leynerjoseoa.
 * @contributors soleimygomez, serbuitrago, jhonatanbeltran.
 * @version 1.0.0.
 */
public interface TypeDocumentService {
	
	TypeDocument findById(Long id);

	TypeDocument findByName(String name);

	List<TypeDocument> findAll();

	TypeDocument save(TypeDocument entity, int type);

	boolean delete(Long id);
}