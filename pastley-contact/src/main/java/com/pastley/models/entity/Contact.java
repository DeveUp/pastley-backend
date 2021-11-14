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
import javax.persistence.Transient;

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
@Table(name = "contact")
@Data
@NoArgsConstructor
public class Contact implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "message", nullable = false, length = 1000)
	private String message;

	@Column(name = "statu", nullable = false, columnDefinition = "tinyint(1) default 1")
	private boolean statu;

	@Column(name = "date_register", nullable = false)
	private String dateRegister;

	@Column(name = "date_update",nullable = true)
	private String dateUpdate;
	
	@Column(name = "id_user", nullable = false)
	private Long idUser;
	
	@ManyToOne
	@JoinColumn(name = "id_type_pqr", nullable = false)
	private TypePQR typePqr;
	
	@Transient
	private Long document;
	@Transient
	private String email;
	@Transient
	private String name;
	
	public String validate(boolean isId) {
		String chain = null;
		if (isId) {
			if (id <= 0) {
				chain = "El id debe ser mayor a cero.";
			}
		}
		if (!PastleyValidate.isChain(message))
			chain = "El mensaje no es valido.";
		if (!PastleyValidate.isChain(email))
			chain = "El email de la persona no es valido.";
		if (!PastleyValidate.isChain(name))
			chain = "El nombre de la persona no es valido.";
		if(document == null || document <= 0)
			chain = "El documento de la persona no es valido.";
		return chain;
	}
}
