package com.pastley.models.service;

import java.util.List;

import com.pastley.models.dto.StatisticDTO;
import com.pastley.models.entity.MethodPay;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public interface MethodPayService{

	MethodPay findById(Long id);

	MethodPay findByName(String name);

	List<MethodPay> findAll();

	List<MethodPay> findByStatuAll(boolean statu);

	List<MethodPay> findByRangeDateRegister(String start, String end);

	Long findByStatisticSalePrivate(Long id);

	StatisticDTO<MethodPay> findByStatisticSale(Long id);

	List<StatisticDTO<MethodPay>> findByStatisticSaleAll();

	MethodPay save(MethodPay entity, int type);

	boolean delete(Long id);
}
