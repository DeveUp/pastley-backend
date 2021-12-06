package com.pastley.models.service;

import java.util.List;

import com.pastley.models.entity.Product;

/**
 * @project Pastley-Product.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public interface ProductService {
	
	Product findById(Long id);

	Product findByName(String name);

	List<Product> findAll();
	
	List<Product> findProductByPromotionAll();

	List<Product> findByStatuAll(boolean statu);
	
	List<Product> findBySuppliesAll(boolean supplies);
	
	List<Product> findByIdCategory(Long idCategory);

	List<Product> findByRangeDateRegister(String start, String end);

	Product save(Product entity, int type);

	boolean delete(Long id);
}
