package com.pastley.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pastley.models.entity.Provider;

/**
 * @project Pastley-Buy.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Repository
public interface ProviderRepository  extends JpaRepository<Provider, Long>{
	
	public Provider findByName(String name);
	
	public List<Provider> findByStatu(boolean statu);
	
	@Query(nativeQuery = false, value = "SELECT p FROM Provider p WHERE c.dateRegister BETWEEN :start AND :end ORDER BY c.dateRegister")
	public List<Provider> findByRangeDateRegister(@Param("start") String start, @Param("end") String end);
}
