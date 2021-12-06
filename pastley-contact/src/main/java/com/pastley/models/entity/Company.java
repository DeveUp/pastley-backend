package com.pastley.models.entity;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project Pastley-Contact.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/serbuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "company")
public class Company implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", unique = true, nullable = false, length = 100)
	private String name;

	@Column(name = "email", unique = true, nullable = false, length = 300)
	private String email;

	@Column(name = "password", nullable = false, length = 500)
	private String password;

	@Column(name = "address", nullable = false, length = 200)
	private String address;

	@Column(name = "description", nullable = true, length = 500)
	private String desciption;

	@Column(name = "mission", nullable = true, length = 500)
	private String mission;

	@Column(name = "vision", nullable = true, length = 500)
	private String vision;

	@Column(name = "about_us", nullable = true, length = 500)
	private String aboutUs;

	@Column(name = "size", nullable = false, length = 10)
	private int size;

	@Column(name = "butdget", nullable = false)
	private BigInteger butdget;

	@Column(name = "logo", nullable = true, length = 500)
	private String logo;

	@Column(name = "statu", nullable = false, columnDefinition = "tinyint(1) default 1")
	private boolean statu;

	@Column(name = "send_sales_mail", nullable = false, columnDefinition = "tinyint(1) default 1")
	private boolean sendSalesMail;

	@Column(name = "alert_stock", nullable = false, columnDefinition = "tinyint(1) default 1")
	private boolean alertStock;

	@Column(name = "alert_min_stock", nullable = false, columnDefinition = "tinyint(1) default 1")
	private Integer alertMinStock;

	@Column(name = "date_register", nullable = false)
	private String dateRegister;

	@Column(name = "date_update", nullable = true)
	private String dateUpdate;
}
