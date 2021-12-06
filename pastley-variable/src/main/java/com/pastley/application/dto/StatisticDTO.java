package com.pastley.application.dto;
import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatisticDTO<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private T entity;
	private Long count;
	
	public StatisticDTO(T entity, Long count) {
		this.entity = entity;
		this.count = count;
	}
}