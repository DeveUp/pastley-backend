package com.pastley.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pastley.model.entity.TypeDocument;

/**
 * @project Pastley-User.
 * @author Leyner Jose Ortega Arias.
 * @Github https://github.com/leynerjoseoa.
 * @contributors serbuitrago.
 * @version 1.0.0.
 */
@Repository
public interface TypeDocumentRepository extends JpaRepository<TypeDocument,Long> {
	
	public TypeDocument findByName(String name);
}
