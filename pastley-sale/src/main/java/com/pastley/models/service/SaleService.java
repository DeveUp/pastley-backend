package com.pastley.models.service;

import java.util.List;

import com.pastley.models.entity.Sale;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public interface SaleService{

	Sale findById(Long id);

	List<Sale> findAll();

	List<Sale> findByIdCustomerAll(Long idCustoner);

	List<Sale> findByIdMethodPayAll(Long idMethodPay);

	List<Sale> findByMonthAndYear(String month, int year);

	List<Sale> findByMonthAndYearCurrent();

	List<Sale> findByStatuAll(boolean statu);

	List<Sale> findByRangeDateRegister(String start, String end);

	Sale save(Sale entity, int type);

	boolean delete(Long id);
}
