package com.pastley.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pastley.util.PastleyValidate;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project Pastley-Sale.
 * @author Soleimy Daniela Gomez Baron.
 * @Github https://github.com/Soleimygomez.
 * @contributors soleimygomez, leynerjoseoa, SerBuitragp jhonatanbeltran.
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

	public String validate(boolean isId) {
		String chain = null;
		if (isId) {
			if (id <= 0) {
				chain = "El id del PQR debe ser mayor a cero.";
			}
		}
		if (!PastleyValidate.isChain(name))
			chain = "El nombre del PQR no es valido.";
		return chain;
	}

	public void uppercase() {
		this.name = PastleyValidate.uppercase(this.name);
	}
}
