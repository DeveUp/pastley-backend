package com.pastley.models.entity;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(name="sale")
public class Sale implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="id_coustomer", nullable = false)
	private Long idCoustomer;
	
	@Column(name="vat", nullable = false, columnDefinition = "varchar(3) default 0")
	private String vat;
	
	@Column(name="total_net", nullable = false)
	private BigInteger totalNet;
	
	@Column(name="total_gross", nullable = false)
	private BigInteger totalGross;
	
	@Column(name="statu", nullable = false, columnDefinition="tinyint(1) default 1")
	private boolean statu;
	
	@Column(name="date_register", nullable = false)
	private String dateRegister;
	
	@Column(name="date_update", nullable = true)
	private String dateUpdate;
	
	@ManyToOne
	@JoinColumn(name = "id_method_pay", nullable = false)
	private MethodPay methodPay;
}
