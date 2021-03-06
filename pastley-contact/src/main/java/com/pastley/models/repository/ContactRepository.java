package com.pastley.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pastley.models.entity.Contact;

/**
 * @project Pastley-Contact.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/serbuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	List<Contact> findByStatu(boolean statu);
	
	List<Contact> findByIdUser(Long idUser);
	
	@Query(nativeQuery = false, value = "SELECT c FROM Contact c WHERE c.typePqr.id = :idTypePqr")
	List<Contact> findByIdTypePqr(Long idTypePqr);
	
	@Query(nativeQuery = false, value = "SELECT c FROM Contact c WHERE c.dateRegister BETWEEN :start AND :end ORDER BY c.dateRegister")
	List<Contact> findByRangeDateRegister(@Param("start") String start, @Param("end") String end);
}
