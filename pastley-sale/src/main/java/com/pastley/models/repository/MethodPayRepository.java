package com.pastley.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pastley.models.entity.MethodPay;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Repository
public interface MethodPayRepository extends JpaRepository<MethodPay, Long>{
	
	public MethodPay findByName(String name);
	
	public List<MethodPay> findByStatu(boolean statu);
	
	@Query(nativeQuery = false, value = "SELECT mp FROM MethodPay mp WHERE mp.dateRegister BETWEEN :start AND :end ORDER BY mp.dateRegister")
	public List<MethodPay> findByRangeDateRegister(@Param("start") String start, @Param("end") String end);
	
	@Query(nativeQuery = false, value = "SELECT COUNT(s.methodPay.id) FROM Sale s WHERE s.methodPay.id = :id GROUP BY s.methodPay.id")
	public Long countByMethodPaySale(Long id);
}
