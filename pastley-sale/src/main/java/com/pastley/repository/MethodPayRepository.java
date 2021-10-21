package com.pastley.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pastley.entity.MethodPay;

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
	
	@Query(nativeQuery = false, value = "SELECT mp FROM MethodPay mp WHERE mp.dateRegister BETWEEN ':start' AND ':end' ORDER BY mp.dateRegister")
	public List<MethodPay> findByRangeDate(@Param("start") String start, @Param("end") String end);
}
