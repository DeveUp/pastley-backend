package com.pastley.infrastructure.config;

import java.util.List;

/**
 * @project Pastley-Variable.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public interface PastleyInterface<T, O> {
	
	public O findById(T id);
	
	public List<O> findAll();
	
	public List<O> findByStatuAll(boolean statu);
	
	public O save(O entity);
	
	public boolean delete(T id);
}
