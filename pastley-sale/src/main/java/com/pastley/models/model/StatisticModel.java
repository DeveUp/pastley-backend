package com.pastley.models.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors soleimygomez, leynerjoseoa, jhonatanbeltran.
 * @version 1.0.0.
 */
@Data
@NoArgsConstructor
public class StatisticModel<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private T entity;
	private Long count;
	
	///////////////////////////////////////////////////////
	// Builder
	///////////////////////////////////////////////////////
	
	public StatisticModel(T entity, Long count) {
		this.entity = entity;
		this.count = count;
	}
}
