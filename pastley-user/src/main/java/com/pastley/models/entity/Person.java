package com.pastley.models.entity;

import java.io.Serializable;

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
 * @project Pastley-User.
 * @author Leyner Jose Ortega Arias.
 * @Github https://github.com/leynerjoseoa.
 * @contributors serbuitrago.
 * @version 1.0.0.
 */
@Entity
@Table(name = "person")
@Data
@NoArgsConstructor
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "document", unique = true, nullable = false)
	private Long document;

	@Column(name = "name", nullable = false, length = 50)
	private String name;

	@Column(name = "subname", nullable = false, length = 50)
	private String subname;

	@Column(name = "phone", nullable = false)
	private String phone;

	@Column(name = "email", unique = true, nullable = false, length = 50)
	private String email;

	@Column(name = "address", length = 50, nullable = true)
	private String address;

	@Column(name = "date_birthday", nullable = true)
	private String dateBirthday;

	@Column(name = "date_register", nullable = false)
	private String dateRegister;

	@Column(name = "date_update", nullable = true)
	private String dateUpdate;

	@ManyToOne
	@JoinColumn(name = "id_type_document", nullable = false)
	private TypeDocument typeDocument;
}