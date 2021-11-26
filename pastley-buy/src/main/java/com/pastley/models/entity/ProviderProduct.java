package com.pastley.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "provider_product")
@Data
@NoArgsConstructor
public class ProviderProduct implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long product;
	@Id
	private Long provider;
}
