package com.pastley.infrastructure.dto;

import java.io.Serializable;

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
public class PersonModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long document;
	private String name;
	private String subname;
	private String phone;
	private String email;
	private String address;
	private String dateBirthday;
	private Long idTypeDocument;

	public PersonModel(Long document, String name, String subname, String phone, String email, String address,
			String dateBirthday, Long idTypeDocument) {
		this(0L, document, name, subname, phone, email, address, dateBirthday, idTypeDocument);
	}

	public PersonModel(Long id, Long document, String name, String subname, String phone, String email, String address,
			String dateBirthday, Long idTypeDocument) {
		this.id = id;
		this.document = document;
		this.name = name;
		this.subname = subname;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.dateBirthday = dateBirthday;
		this.idTypeDocument = idTypeDocument;
	}
}