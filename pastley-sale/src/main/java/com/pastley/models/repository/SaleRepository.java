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
	
	/**
	 * Method that allows you to check sales by their status.
	 * @param statu, Represents the state.
	 * @return List with sales found.
	 */
	public List<Sale> findByStatu(boolean statu);
	
	/**
	 * Method that allows knowing all the sales of a user.
	 * @param idCoustomer, Represents the id of the user.
	 * @return List with sales found.
	 */
	public List<Sale> findByIdCoustomer(Long idCoustomer);
	
	/**
	 * Method that allows to know the sales by a payment method.
	 * @param idMethodPay, Represents the id of the payment method.
	 * @return @return List with sales found.
	 */
	public List<Sale> findByIdMethodPay(Long idMethodPay);
	
	
	/**
	 * Method that allows you to filter the sales that are registered between a range of dates.
	 * @param start, Represents the start date.
	 * @param end, Represents the end date.
	 * @return List with sales found.
	 */
	@Query(nativeQuery = false, value = "SELECT s FROM Sale s WHERE s.dateRegister BETWEEN :start AND :end ORDER BY s.dateRegister")
	public List<Sale> findByRangeDateRegister(@Param("start") String start, @Param("end") String end);
	
	/**
	 * Method that allows to consult all the sales of a month and year indicated.
	 * @param month, Represents the month.
	 * @param year, Represents the year.
	 * @return List with sales found.
	 */
	@Query(nativeQuery = false, value = "SELECT s FROM Sale s WHERE MONTHNAME(s.dateRegister) = :month AND YEAR(s.dateRegister) = :year ORDER BY s.dateRegister")
	public List<Sale> findByMonthAndYear(@Param("month") String month, @Param("year") int year);
}
