package com.pastley.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pastley.models.entity.ContactResponse;

/**
 * @project Pastley-Contact.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/serbuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Repository
public interface ContactResponseRepository extends JpaRepository<ContactResponse, Long>{
	
	@Query(nativeQuery = false, value = "SELECT cr FROM ContactResponse cr WHERE cr.contact.id = :idContact")
	public List<ContactResponse> findByIdContact(Long idContact);
	
	@Query(nativeQuery = false, value = "SELECT cr FROM ContactResponse cr WHERE cr.dateRegister BETWEEN :start AND :end ORDER BY cr.dateRegister")
	public List<ContactResponse> findByRangeDateRegister(@Param("start") String start, @Param("end") String end);
}
