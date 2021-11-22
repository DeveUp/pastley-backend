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
 * @contributors soleimygomez, leynerjoseoa, jhonatanbeltran.
 * @version 1.0.0.
 */
@Repository
public interface MethodPayRepository extends JpaRepository<MethodPay, Long>{
	
	/**
	 * Method that allows you to consult a payment method by name
	 * @param name, Represents the name.
	 * @return The payment method found.
	 */
	public MethodPay findByName(String name);
	
	/**
	 * Method that allows you to check all the payment methods for their status.
	 * @param statu, Represents the state.
	 * @return A list with the payment methods found.
	 */
	public List<MethodPay> findByStatu(boolean statu);
	
	/**
	 * Method that allows filtering the payment methods that are registered between a date range.
	 * @param start, Represents the start date.
	 * @param end, Represents the end date.
	 * @return A list with the payment methods found.
	 */
	@Query(nativeQuery = false, value = "SELECT mp FROM MethodPay mp WHERE mp.dateRegister BETWEEN :start AND :end ORDER BY mp.dateRegister")
	public List<MethodPay> findByRangeDateRegister(@Param("start") String start, @Param("end") String end);
	
	/**
	 * Method that allows to know the amount of sales made by a payment method.
	 * @return A list with the payment methods found.
	 */
	@Query(nativeQuery = false, value = "SELECT COUNT(s.idMethodPay) FROM Sale s WHERE s.idMethodPay = :id GROUP BY s.idMethodPay")
	public Long countByMethodPaySale(Long id);
}
