package com.pastley.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.pastley.util.PastleyValidate;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
    private Long id;
    
	@Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;
	
	@Column(name = "statu", nullable = true, columnDefinition = "tinyint(1) default 1")
    private boolean statu;

	@Column(name="date_register", nullable = false)
	private String dateRegister;
	
	@Column(name="date_update", nullable = true)
	private String dateUpdate;
	
	public String validate(boolean isId) {
		String chain = null;
		if (isId) {
			if (id <= 0) {
				chain = "El id de la categoria debe ser mayor a cero.";
			}
		}
		if (!PastleyValidate.isChain(name)) {
			chain = "El nombre del la categoria no es valido.";
		}
		return chain;
	}
	
	/**
	 * Convert variables to uppercase.
	 */
	public void uppercase() {
		this.name = PastleyValidate.uppercase(this.name);
	}
}