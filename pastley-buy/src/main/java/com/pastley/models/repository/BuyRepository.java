package com.pastley.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pastley.models.domain.Buy;

@Repository
public interface BuyRepository extends JpaRepository<Buy, Long> {
	
	public List<Buy> findByStatu(boolean statu);
	
	@Query(nativeQuery = false, value = "SELECT b FROM Buy b WHERE b.provider.id = :idProvider")
	public List<Buy> findByProvider(@Param("idProvider") Long idProvider);

	@Query(nativeQuery = false, value = "SELECT b FROM Buy b WHERE b.dateRegister BETWEEN :start AND :end ORDER BY b.dateRegister")
	public List<Buy> findByRangeDateRegister(@Param("start") String start, @Param("end") String end);
}
