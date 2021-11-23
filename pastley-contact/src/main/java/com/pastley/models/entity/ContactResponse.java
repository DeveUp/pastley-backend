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

import com.pastley.util.PastleyValidate;

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
@Table(name = "contact_response")
@Data
@NoArgsConstructor
public class ContactResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "response", nullable = false, length = 250)
	private String response;
	
	@Column(name = "date_register", nullable = false)
	private String dateRegister;
	
	@Column(name = "date_update", nullable = true)
	private String dateUpdate;
	
	@ManyToOne
	@JoinColumn(name = "id_contact", nullable = false)
	private Contact contact;
	
	public String validate() {
		String chain = null;
		if (!PastleyValidate.isChain(response))
			chain = "El mensaje de respuesta no es valido.";
		if(contact == null || contact.getId() <= 0)
			chain = "El contacto no es valido.";
		return chain;
	}
}
