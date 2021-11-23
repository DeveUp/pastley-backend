package com.pastley.models.model;
import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatisticModel<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private T entity;
	private Long count;
	
	public StatisticModel(T entity, Long count) {
		this.entity = entity;
		this.count = count;
	}
}