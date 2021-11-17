package com.pastley.model.entity;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "name", nullable = false, unique = true, length = 100)
	private String name;
	
	@Column(name = "flavor", nullable = true, length = 500)
	private String flavor;
	
	@Column(name = "vat", nullable = false, length = 3)
	private String vat;
	
	@Column(name = "stock", nullable = false)
	private int stock;

	@Column(name = "stock_min", nullable = false)
	private int stockMin;
	
	@Column(name = "dimension", nullable = true)
	private String dimension;
	
	@Column(name = "image", nullable = true, length = 500)
	private String image;
	
	@Column(name = "statu", nullable = true, columnDefinition = "tinyint(1) default 1")
	private boolean statu;
	
	@Column(name = "description", nullable = true, length = 500)
	private String description;
	
	@Column(name = "ingredients", nullable = true, length = 500)
	private String ingredients;
	
	@Column(name = "discount", nullable = false)
	private String discount;
	
	@Column(name = "price", nullable = false)
	private BigInteger price;

	@Column(name="date_register", nullable = false)
	private String dateRegister;
	
	@Column(name="date_update", nullable = true)
	private String dateUpdate;

	@ManyToOne
	@JoinColumn(name = "id_category")
	private Category category;

}
