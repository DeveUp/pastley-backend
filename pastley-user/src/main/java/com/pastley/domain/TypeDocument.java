package com.pastley.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "type_document")
@Data
@NoArgsConstructor
public class TypeDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "statu", nullable = false, columnDefinition = "tinyint(1) default 1")
	private boolean statu;

	@Column(name = "date_register", nullable = false)
	private String dateRegister;

	@Column(name = "date_update", nullable = true)
	private String dateUpdate;

	/**
	 * Method that validates the attributes of the class.
	 * 
	 * @param isId, Represents if you want to validate the id.
	 * @return The error occurred.
	 */
	public String validate() {
		String chain = null;
		if (!PastleyValidate.isChain(name))
			chain = "El nombre del tipo de docuemto no es valido.";
		return chain;
	}

	/**
	 * Convert variables to uppercase.
	 */
	public void uppercase() {
		this.name = PastleyValidate.uppercase(this.name);
	}
}