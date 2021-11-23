package com.pastley.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pastley.models.entity.TypePQR;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @project Pastley-Contact.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/serbuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Repository
public interface TypePQRRepository  extends JpaRepository<TypePQR, Long>{
	
	public TypePQR findByName(String name);
	
	public List<TypePQR> findByStatu(boolean statu);
	
	@Query(nativeQuery = false, value = "SELECT t FROM TypePQR t WHERE t.dateRegister BETWEEN :start AND :end ORDER BY t.dateRegister")
	public List<TypePQR> findByRangeDateRegister(@Param("start") String start, @Param("end") String end);

	@Query(nativeQuery = false, value = "SELECT COUNT(c.typePqr.id) FROM Contact c WHERE c.typePqr.id = :id GROUP BY c.typePqr.id")
	public Long countByTypePQR(Long id);
}
