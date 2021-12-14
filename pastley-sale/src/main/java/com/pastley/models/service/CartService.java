package com.pastley.models.service;

import java.util.List;

import com.pastley.models.entity.Cart;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public interface CartService{

	Cart findById(Long id);
	
	Cart findByCustomerAndProductAndStatu(boolean statu, Long idCustomer, Long idProduct);

	List<Cart> findAll();

	List<Cart> findByStatuAll(boolean statu);

	List<Cart> findByCustomer(Long idCustomer);

	List<Cart> findByCustomerAndStatus(Long idCustomer, boolean statu);

	List<Cart> findByCustomerAndProduct(Long idCustomer, Long idProduct);

	List<Cart> findByProductAndStatus(Long idProduct, boolean statu);

	List<Cart> findByRangeDateRegister(String start, String end);

	List<Cart> findByRangeDateRegisterAndCustomer(Long idCustomer, String start, String end);
	
	Cart save(Cart entity, int type); 

	boolean delete(Long id);
}
