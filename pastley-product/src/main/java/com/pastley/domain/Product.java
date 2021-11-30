package com.pastley.domain;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

import com.pastley.infrastructure.config.PastleyValidate;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project Pastley-Product.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "name", nullable = false, unique = true, length = 100)
	private String name;
	
	@Column(name = "flavor", nullable = true, length = 500)
	private String flavor;
	
	@Column(name = "vat", nullable = false, length = 3)
	private String vat;
	
	@Column(name = "stock", nullable = false)
	private int stock;

	@Column(name = "stock_min", nullable = false)
	private int stockMin;
	
	@Column(name = "dimension", nullable = true)
	private String dimension;
	
	@Column(name = "image", nullable = true, length = 500)
	private String image;
	
	@Column(name = "statu", nullable = true, columnDefinition = "tinyint(1) default 1")
	private boolean statu;
	
	@Column(name = "supplies", nullable = true, columnDefinition = "tinyint(1) default 1")
	private boolean supplies;
	
	@Column(name = "description", nullable = true, length = 500)
	private String description;
	
	@Column(name = "ingredients", nullable = true, length = 500)
	private String ingredients;
	
	@Column(name = "discount", nullable = false)
	private String discount;
	
	@Column(name = "price", nullable = false)
	private BigInteger price;

	@Column(name="date_register", nullable = false)
	private String dateRegister;
	
	@Column(name="date_update", nullable = true)
	private String dateUpdate;

	@ManyToOne
	@JoinColumn(name = "id_category")
	private Category category;
	
	public String validate() {
		String chain = null;
		if (!PastleyValidate.isChain(name))
			chain = "El nombre del producto no es valido.";
		if (!PastleyValidate.isChain(vat))
			chain = "El iva del producto no es valido.";
		if(!PastleyValidate.isNumber(vat))
			chain = "El iva solo debe tener caracteres numericos.";
		if(PastleyValidate.isChain(vat) && vat.length() > 3)
			chain = "El iva es un porcentaje de 0 a 100 no puede ser superior.";
		if(!PastleyValidate.bigIntegerHigherZero(price))
			chain = "El precio del producto no es valido.";
		if (!PastleyValidate.isChain(discount))
			chain = "El descuento del producto no es valido.";
		if(!PastleyValidate.isNumber(discount))
			chain = "El descuento solo debe tener caracteres numericos.";
		if(PastleyValidate.isChain(discount) && discount.length() > 3)
			chain = "El descuento es un porcentaje de 0 a 100 no puede ser superior.";
		if (stock<0)
			chain = "El stock debe ser mayor o igual a cero.";
		if (stockMin<=0)
			chain = "El stock minimo debe ser mayor a cero.";
		if(category == null || category.getId() <= 0)
			chain = "La categoria no es valida.";
		return chain;
	}
	
	/**
	 * Convert variables to uppercase.
	 */
	public void uppercase() {
		this.name = PastleyValidate.uppercase(this.name);
	}

}
