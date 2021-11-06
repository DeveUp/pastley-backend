package com.pastley.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.pastley.util.PastleyValidate;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors soleimygomez, leynerjoseoa, jhonatanbeltran.
 * @version 1.0.0.
 */
@Data
@NoArgsConstructor
public class ProductModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private BigInteger price;
	private String discount;
	
	private int stock;
	private String image;

	///////////////////////////////////////////////////////
	// Other
	///////////////////////////////////////////////////////
	private String vat;

	private BigInteger priceVat;
	private BigInteger priceDiscount;

	///////////////////////////////////////////////////////
	// Builder
	///////////////////////////////////////////////////////

	public ProductModel(Long id, String name, BigInteger price, String discount) {
		this(id, name, price, discount, 0, null, null);
	}

	public ProductModel(BigInteger price, String discount, String vat) {
		this(0L, null, price, discount, 0, null, vat);
	}

	public ProductModel(Long id, BigInteger price, String discount, String vat) {
		this(id, null, price, discount, 0, null, vat);
	}

	public ProductModel(Long id, String name, BigInteger price, String discount, String vat) {
		this(id, name, price, discount, 0, null, vat);
	}

	public ProductModel(Long id, String name, BigInteger price, String discount, int stock, String image, String vat) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.discount = discount;
		this.stock = stock;
		this.image = image;
		this.vat = vat;
	}

	///////////////////////////////////////////////////////
	// Method - Validate
	///////////////////////////////////////////////////////
	/**
	 * Method that validates the attributes of the class.
	 * 
	 * @param isId, Represents if you want to validate the id.
	 * @return The error occurred.
	 */
	public String validate(boolean isId) {
		String chain = null;
		if (isId) {
			if (id <= 0) {
				chain = "El id del producto debe ser mayor a cero.";
			}
		}
		if (!PastleyValidate.isChain(name)) {
			chain = "El nombre del producto no es valido.";
		}
		if (!PastleyValidate.isChain(discount)) {
			chain = "El descuento del producto no es valido.";
		}else {
			if(!PastleyValidate.isNumber(discount)) {
				chain = "El descuento del producto solo puede tener caracteres numericos.";
			}
		}
		if (!PastleyValidate.bigIntegerHigherZero(price)) {
			chain = "El precio del producto no es valido.";
		}
		if(vat != null) {
			if(!PastleyValidate.isNumber(vat)) {
				chain = "El iva del producto solo puede tener caracteres numericos.";
			}
		}
		return chain;
	}

	///////////////////////////////////////////////////////
	// Method - Calculate
	///////////////////////////////////////////////////////
	/**
	 * Method that allows all prices to be calculated.
	 */
	public void calculate() {
		this.calculatePriceIva();
		this.calculateDiscount();

	}

	/**
	 * Method that allows calculating the price of vat.
	 */
	public void calculatePriceIva() {
		this.priceVat = calculate(this.vat);
	}

	/**
	 * Method that allows the discount price to be calculated.
	 */
	public void calculateDiscount() {
		this.priceDiscount = calculate(this.discount);
	}

	/**
	 * Method for calculating the net subtotal.
	 * 
	 * @return The value obtained.
	 */
	public BigInteger calculateSubTotalNet() {
		return calculatePriceSubDiscount();
	}

	/**
	 * Method for calculating the gross subtotal.
	 * 
	 * @return The value obtained.
	 */
	public BigInteger calculateSubtotalGross() {
		if (!PastleyValidate.bigIntegerHigherZero(this.priceVat)) {
			calculatePriceIva();
		}
		return calculateSubTotalNet().add(this.priceVat);
	}

	/**
	 * Method that allows adding the value of the VAT to the price of the product.
	 * 
	 * @return The value obtained.
	 */
	public BigInteger calculatePriceAddPriceIva() {
		if (!PastleyValidate.bigIntegerHigherZero(this.priceVat)) {
			calculatePriceIva();
		}
		BigInteger price = (PastleyValidate.bigIntegerHigherZero(this.price)) ? this.price.add(this.priceVat)
				: BigInteger.ZERO;
		return (PastleyValidate.bigIntegerLessZero(price)) ? BigInteger.ZERO : price;
	}

	/**
	 * Method that allows subtracting the value of the discount from the price of
	 * the product.
	 * 
	 * @return The value obtained.
	 */
	public BigInteger calculatePriceSubDiscount() {
		if (!PastleyValidate.bigIntegerHigherZero(this.priceDiscount)) {
			calculateDiscount();
		}
		BigInteger price = (PastleyValidate.bigIntegerHigherZero(this.price)) ? this.price.subtract(this.priceDiscount)
				: BigInteger.ZERO;
		return (PastleyValidate.bigIntegerLessZero(price)) ? BigInteger.ZERO : price;
	}

	///////////////////////////////////////////////////////
	// Method - Private
	///////////////////////////////////////////////////////
	/**
	 * Method that allows you to convert a percentage into a price.
	 * 
	 * @param chain, Represents the percentage.
	 * @return The value obtained.
	 */
	private BigInteger calculate(String chain) {
		if (PastleyValidate.isChain(chain)) {
			BigDecimal price = new BigDecimal(this.price);
			int number = (PastleyValidate.isNumber(chain)) ? Integer.parseInt(chain) : 0;
			price = price.multiply(new BigDecimal((double) (number / 100d)));
			return price.toBigInteger();
		}
		return BigInteger.ZERO;
	}
}
