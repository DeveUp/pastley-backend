package com.pastley.domain;

import java.io.Serializable;
import java.math.BigInteger;

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
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="sale")
public class Sale implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="id_coustomer", nullable = false)
	private Long idCoustomer;
	
	@Column(name = "id_method_pay", nullable = false)
	private Long idMethodPay;
	
	@Column(name="iva", nullable = false, length = 3)
	private String iva;
	
	@Column(name="total_net", nullable = false)
	private BigInteger totalNet;
	
	@Column(name="total_gross", nullable = false)
	private BigInteger totalGross;
	
	@Column(name="statu", nullable = false, columnDefinition="tinyint(1) default 1")
	private boolean statu;
	
	@Column(name="date_register", nullable = false)
	private String dateRegister;
	
	@Column(name="date_update", nullable = true)
	private String dateUpdate;
	
	/**
	 * Method that validates the attributes of the class.
	 * @param isId, Represents if you want to validate the id.
	 * @return The error occurred.
	 */
	public String validate() {
		String chain = null;
		if(idCoustomer <= 0)
			chain = "No se ha recibido el cliente de la venta.";
		if(idMethodPay <= 0)
			chain = "No se ha recibido el metodo de pago de la venta.";
		if(!PastleyValidate.isChain(iva))
			chain = "No se ha recibido el iva de la venta.";
		if(!PastleyValidate.bigIntegerHigherZero(totalGross) || !PastleyValidate.bigIntegerHigherZero(totalNet))
			chain = "El total neto o bruto de la venta debe ser mayor a cero.";
		return chain;
	}
}
