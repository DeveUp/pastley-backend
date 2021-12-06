package com.pastley.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pastley.models.entity.TypeDocument;

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
