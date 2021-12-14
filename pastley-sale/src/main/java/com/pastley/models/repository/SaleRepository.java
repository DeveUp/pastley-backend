package com.pastley.models.repository;

import org.springframework.stereotype.Repository;

import com.pastley.models.entity.Sale;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>{
	
	public List<Sale> findByStatu(boolean statu);
	
	public List<Sale> findByIdCoustomer(Long idCoustomer);
	
	public List<Sale> findByMethodPay(Long idMethodPay);
	
	@Query(nativeQuery = false, value = "SELECT s FROM Sale s WHERE s.dateRegister BETWEEN :start AND :end ORDER BY s.dateRegister")
	public List<Sale> findByRangeDateRegister(@Param("start") String start, @Param("end") String end);
	
	@Query(nativeQuery = false, value = "SELECT s FROM Sale s WHERE MONTHNAME(s.dateRegister) = :month AND YEAR(s.dateRegister) = :year ORDER BY s.dateRegister")
	public List<Sale> findByMonthAndYear(@Param("month") String month, @Param("year") int year);
}
