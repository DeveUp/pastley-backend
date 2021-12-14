package com.pastley.models.service;

import java.util.List;

import com.pastley.models.entity.SaleDetail;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public interface SaleDetailService{

	SaleDetail findById(Long id);

	List<SaleDetail> findAll();

	List<SaleDetail> findBySale(Long idSale);

	SaleDetail save(SaleDetail entity);

	boolean delete(Long id);
}
