package com.pastley.models.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pastley.models.dto.ProductDTO;
import com.pastley.models.entity.Sale;
import com.pastley.models.service.SaleService;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Service
public class SaleServiceImpl implements SaleService{

	@Override
	public Sale findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sale> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sale> findByIdCustomerAll(Long idCustoner) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sale> findByIdMethodPayAll(Long idMethodPay) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sale> findByMonthAndYear(String month, int year) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sale> findByMonthAndYearCurrent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sale> findByStatuAll(boolean statu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sale> findByRangeDateRegister(String start, String end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sale save(Sale entity, int type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public ProductDTO findProductById(Long idProduct) {
		return null;
	}

}
