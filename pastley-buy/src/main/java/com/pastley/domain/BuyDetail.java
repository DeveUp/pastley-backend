package com.pastley.domain;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.pastley.infrastructure.dto.SaleDTO;
import com.pastley.infrastructure.config.PastleyValidate;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project Pastley-Buy.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "buy_detail")
public class BuyDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	private Long idProduct;

	@Column(name = "discount", nullable = false, columnDefinition = "varchar(3) default 0")
	private String discount;

	@Column(name = "vat", nullable = false, length = 3)
	private String vat;

	@Column(name = "count", nullable = false)
	private int count;

	@Column(name = "price", nullable = false)
	private BigInteger price;

	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "subtotal_net", nullable = false)
	private BigInteger subtotalNet;

	@Column(name = "subtotal_gross", nullable = false)
	private BigInteger subtotalGross;

	@ManyToOne
	@JoinColumn(name = "id_buy")
	private Buy buy;

	@Transient
	private BigInteger otherPriceVat;
	@Transient
	private BigInteger otherPriceAddPriceVat;
	@Transient
	private BigInteger otherPriceDisount;
	@Transient
	private BigInteger otherPriceSubPriceDisount;
	@Transient
	private BigInteger otherSubtotalPriceDisount;

	public String validate(boolean isBuy) {
		String message = null;
		if (!PastleyValidate.isChain(discount))
			message = "El descuento no es valido.";
		if (PastleyValidate.isChain(discount) && discount.length() > 3)
			message = "El descuento es un porcentaje de 0 a 100.";
		if (!PastleyValidate.isChain(vat))
			message = "El iva no es valido.";
		if (PastleyValidate.isChain(vat) && vat.length() > 3)
			message = "El iva es un porcentaje de 0 a 100.";
		if (!PastleyValidate.isLong(idProduct))
			message = "El id del producto no es valido.";
		if (count <= 0)
			message = "La cantidad no es valida.";
		if (isBuy)
			if (buy == null || !PastleyValidate.isLong(buy.getId()))
				message = "El id de la compra no es valido.";
		return message;
	}

	public void calculate() {
		SaleDTO dto = new SaleDTO(vat, discount, count, price);
		dto.calculate();
		this.otherPriceVat = dto.getOtherPriceVat();
		this.otherPriceAddPriceVat = dto.getOtherPriceAddPriceVat();
		this.otherPriceDisount = dto.getOtherPriceDisount();
		this.otherPriceSubPriceDisount = dto.getOtherPriceSubPriceDisount();
		this.otherSubtotalPriceDisount = dto.getOtherSubtotalPriceDisount();
		this.subtotalGross = dto.getSubtotalGross();
		this.subtotalNet = dto.getSubtotalNet();
	}
	
	public void addDescription() {
		this.description = "Se han comprado "+count+" insumos del producto id "+idProduct+".";
	}
}
