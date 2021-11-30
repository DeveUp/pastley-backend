package com.pastley.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pastley.infrastructure.config.PastleyValidate;

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

	/**
	 * Method that validates the attributes of the class.
	 * 
	 * @return The error occurred.
	 */
	public String validate() {
		String chain = null;
		if (!PastleyValidate.isChain(name))
			chain = "El nombre de la persona no es valido.";
		if (!PastleyValidate.isChain(subname))
			chain = "El apellido de la persona no es valido.";
		if (!PastleyValidate.isChain(phone))
			chain = "El telefono de la persona no es valido.";
		if (!PastleyValidate.isChain(email))
			chain = "El email de la persona no es valido.";
		if (!PastleyValidate.isEmail(email))
			chain = "El email de la persona no cumple el formato de un email.";
		if (document <= 0)
			chain = "El documento de la persona no es valido.";
		if (typeDocument != null && typeDocument.getId() <= 0)
			chain = "El tipo de documento de la persona no es valido.";
		return chain;
	}

	/**
	 * Convert variables to uppercase.
	 */
	public void uppercase() {
		this.name = PastleyValidate.uppercase(this.name);
		this.subname = PastleyValidate.uppercase(this.subname);
	}
}