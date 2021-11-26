package com.pastley.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "provider")
@Data
@NoArgsConstructor
public class Provider implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name", unique = true, nullable = false, length = 50)
	private String name;
	
	@Column(name = "description", nullable = true, length = 100)
	private String description;
	
	@Column(name = "statu", nullable = false, columnDefinition = "tinyint(1) default 1")
	private boolean statu;
	
	@Column(name="date_register", nullable = false)
	private String dateRegister;
	
	@Column(name="date_update", nullable = true)
	private String dateUpdate;
	
	@OneToMany(mappedBy = "product")
	private List<ProviderProduct> productos;
}
