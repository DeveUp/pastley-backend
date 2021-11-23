package com.pastley.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pastley.model.entity.Person;

/**
 * @project Pastley-User.
 * @author Leyner Jose Ortega Arias.
 * @Github https://github.com/leynerjoseoa.
 * @contributors serbuitrago.
 * @version 1.0.0.
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
	
	public Person findByEmail(String email);
	
	public Person findByDocument(Long document);
	
	@Query(nativeQuery = false, value = "SELECT p FROM Person p WHERE p.typeDocument.id = :idTypeDocument")
	public List<Person> findByIdTypeDocument(Long idTypeDocument);	
}
