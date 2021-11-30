package com.pastley.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project Pastley-Buy.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "provider_product")
public class ProviderProduct implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long product;
	@Id
	private Long provider;
}
