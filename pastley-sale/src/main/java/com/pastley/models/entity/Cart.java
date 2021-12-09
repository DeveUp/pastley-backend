package com.pastley.models.entity;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "id_product", nullable = false)
	private Long idProduct;

	@Column(name = "id_customer", nullable = false)
	private Long idCustomer;

	@Column(name = "discount", nullable = false, columnDefinition = "varchar(3) default 0")
	private String discount;

	@Column(name = "vat", nullable = false, columnDefinition = "varchar(3) default 0")
	private String vat;

	@Column(name = "count", nullable = false)
	private int count;

	@Column(name = "price", nullable = false)
	private BigInteger price;

	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "subtotal_net", nullable = false)
	private BigInteger subtotalNet;

	@Column(name = "subtotal_gross", nullable = false)
	private BigInteger subtotalGross;

	@Column(name = "statu", nullable = false, columnDefinition = "tinyint(1) default 1")
	private boolean statu;
	
	@Column(name="date_register", nullable = false)
	private String dateRegister;
	
	@Column(name="date_update", nullable = true)
	private String dateUpdate;
	
	@Transient
	private Long documentCustomer;
	@Transient
	private BigInteger otherPriceVat;
	@Transient 
	private BigInteger otherPriceAddPriceVat;
	@Transient 
	private BigInteger otherPriceDisount;
	@Transient 
	private BigInteger otherPriceSubPriceDisount;
	@Transient 
	private BigInteger otherSubtotalPriceDisount;
	
	public Cart(String discount, String iva, BigInteger price) {
		this(0L, discount, iva, price);
	}
	
	public Cart(String discount, String iva, BigInteger price, int count) {
		this(0L, discount, iva, price);
		this.count = count;
	}

	public Cart(Long id, String discount, String vat, BigInteger price) {
		this.id = id;
		this.discount = discount;
		this.vat = vat;
		this.price = price;
	}
}
