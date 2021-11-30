package com.pastley.infrastructure.dto;

import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaleDTO {
	
	private String vat;
	private String discount;
	private int count;
	
	private BigInteger price;
	private BigInteger subtotalNet;
	private BigInteger subtotalGross;
	
	private BigInteger otherPriceVat;
	private BigInteger otherPriceAddPriceVat;
	private BigInteger otherPriceDisount;
	private BigInteger otherPriceSubPriceDisount;
	private BigInteger otherSubtotalPriceDisount;
	
	
	public SaleDTO(String vat, String discount, int count, BigInteger price) {
		this.vat = vat;
		this.discount = discount;
		this.count = count;
		this.price = price;
	}
	
	/**
	 * Method that allows all prices to be calculated.
	 */
	public void calculate() {
		if(this.count <= 0) return;
		ProductDTO pm = new ProductDTO(this.price, this.discount, this.vat);
		pm.calculate();
		this.otherPriceVat = pm.getPriceVat();
		this.otherPriceAddPriceVat = pm.calculatePriceAddPriceIva();
		this.otherPriceDisount = pm.getPriceDiscount();
		this.otherPriceSubPriceDisount = pm.calculatePriceSubDiscount();
		calculateSubtotalPriceDisount(pm);
		calculateSubtotalNet(pm);
		calculateSubtotalGross(pm);
	}
	
	/**
	 * Method for calculating the gross subtotal.
	 * @return The value obtained.
	 */
	public BigInteger calculateSubtotalGross(ProductDTO pm) {
		pm = (pm != null) ? pm : new ProductDTO(this.price, this.discount, this.vat);
		this.subtotalGross = pm.calculateSubtotalGross().multiply(new BigInteger(String.valueOf(this.count)));
		return this.subtotalGross;
	}
	
	/**
	 * Method for calculating the net subtotal.
	 * @return The value obtained.
	 */
	public BigInteger calculateSubtotalNet(ProductDTO pm) {
		pm = (pm != null) ? pm : new ProductDTO(this.price, this.discount, this.vat);
		this.subtotalNet = pm.calculateSubTotalNet().multiply(new BigInteger(String.valueOf(this.count)));
		return this.subtotalNet;
	}
	
	/**
	 * Method that allows calculating the subtotal of discount applied.
	 * @return The value obtained.
	 */
	public BigInteger calculateSubtotalPriceDisount(ProductDTO pm) {
		pm = (pm != null) ? pm : new ProductDTO(this.price, this.discount, this.vat);
		pm.calculateDiscount();
		this.otherSubtotalPriceDisount = pm.getPriceDiscount().multiply(new BigInteger(String.valueOf(this.count)));
		return this.otherSubtotalPriceDisount;
	}
}
