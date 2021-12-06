package com.pastley.application.service;

import java.util.List;

import com.pastley.domain.Category;

/**
 * @project Pastley-Product.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public interface CategoryService {

	Category findById(Long id);

	Category findByName(String name);

	List<Category> findAll();

	List<Category> findByStatuAll(boolean statu);

	List<Category> findByRangeDateRegister(String start, String end);

	Category save(Category entity, int type);

	boolean delete(Long id);
}
