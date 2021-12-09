package com.pastley.models.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pastley.models.entity.Cart;
import com.pastley.models.service.CartService;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Service
public class CartServiceImpl implements CartService{

	@Override
	public Cart findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cart findByCustomerAndProductAndStatu(boolean statu, Long idCustomer, Long idProduct) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cart> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cart> findByStatuAll(boolean statu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cart> findByCustomer(Long idCustomer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cart> findByCustomerAndStatus(Long idCustomer, boolean statu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cart> findByCustomerAndProduct(Long idCustomer, Long idProduct) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cart> findByProductAndStatus(Long idProduct, boolean statu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cart> findByRangeDateRegister(String start, String end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cart> findByRangeDateRegisterAndCustomer(Long idCustomer, String start, String end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cart save(Cart entity, int type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
