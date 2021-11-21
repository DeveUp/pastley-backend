package com.pastley.entity;

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
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "sale_detail")
public class SaleDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "id_sale", nullable = false)
	private Long idSale;

	@ManyToOne
	@JoinColumn(name = "id_cart", nullable = false)
	private Cart cart;

	/**
	 * Method that validates the attributes of the class.
	 * 
	 * @param isId, Represents if you want to validate the id.
	 * @return A string with the error occurred.
	 */
	public String validate(boolean isId) {
		String chain = null;
		if (isId && id <= 0)
			chain = "El id del detalle de venta debe ser mayor a cero.";
		if (idSale <= 0)
			chain = "No has seleccionado la venta que esta asociada a este detalle.";
		if (cart == null || cart.getId() <= 0)
			chain = "El producto del carrito no es valido.";
		return chain;
	}
}