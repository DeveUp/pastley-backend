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
 * @project Pastley-Contact.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/serbuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Entity
@Table(name = "type_pqr")
@Data
@NoArgsConstructor
public class TypePQR implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", unique = true, nullable = false, length = 50)
	private String name;

	@Column(name = "description", nullable = true, length = 500)
	private String description;

	@Column(name = "statu", nullable = false, columnDefinition = "tinyint(1) default 1")
	private boolean statu;
	 
	@Column(name = "date_register", nullable = false)
	private String dateRegister;
	 
	@Column(name = "date_update", nullable = true)
	private String dateUpdate;

	public String validate() {
		String chain = null;
		if (!PastleyValidate.isChain(name))
			chain = "El nombre del PQR no es valido.";
		return chain;
	}

	public void uppercase() {
		this.name = PastleyValidate.uppercase(this.name);
	}
}
