package com.pastley.models.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.pastley.util.PastleyValidate;

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
@Table(name = "buy")
public class Buy implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_proveedor", nullable = false)
	private Provider provider;

	@Column(name = "total_net", nullable = false)
	private BigInteger totalNet;

	@Column(name = "total_gross", nullable = false)
	private BigInteger totalGross;

	@Column(name = "statu", nullable = false, columnDefinition = "tinyint(1) default 1")
	private boolean statu;

	@Column(name = "date_register", nullable = false)
	private String dateRegister;

	@Column(name = "date_update", nullable = true)
	private String dateUpdate;

	@Transient
	private List<BuyDetail> details;

	public String validate() {
		String message = null;
		if (provider == null || !PastleyValidate.isLong(provider.getId()))
			message = "El id del proveedor no es valido.";
		return message;
	}

	public void calculate() {
		totalNet = BigInteger.ZERO;
		totalGross = BigInteger.ZERO;
		calculateTotal();
	}

	public BigInteger calculateTotal() {
		if (!PastleyValidate.isList(details))
			return BigInteger.ZERO;
		BigInteger value = BigInteger.ZERO;
		for (BuyDetail bt : details) {
			String message = bt.validate(false);
			if(message == null) {
				bt.calculate();
				if (PastleyValidate.bigIntegerHigherZero(bt.getSubtotalNet()))
					totalNet = totalNet.add(bt.getSubtotalNet());
				if(PastleyValidate.bigIntegerHigherZero(bt.getSubtotalGross()))
					totalGross = totalGross.add(bt.getSubtotalGross());
			}else {
				System.out.println(message);
			}
			
		}
		return value;
	}
}
