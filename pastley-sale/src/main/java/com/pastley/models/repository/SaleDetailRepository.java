package com.pastley.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pastley.models.entity.SaleDetail;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors soleimygomez, leynerjoseoa, jhonatanbeltran.
 * @version 1.0.0.
 */
@Repository
public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long>{
	
	/**
	 * Method that allows knowing the sale details of a sale made.
	 * @param sale, Represents the sale.
	 * @return List with sale details.
	 */
	public List<SaleDetail> findByIdSale(Long sale);
}
