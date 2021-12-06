package com.pastley.application.validator;

import java.math.BigInteger;

import com.pastley.application.exception.PastleyException;
import com.pastley.domain.Product;

/**
 * @project Pastley-Product.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public class ProductValidator {

	public static void validator(Product product) throws PastleyException {
		PastleyModelValidate validate = new PastleyModelValidate();
		if (product == null)
			throw new PastleyException("No se ha podido validar el producto.");
		if (product.getCategory() == null)
			throw new PastleyException("No se ha podido validar la categoria del producto.");
		validate.isString(product.getName(), "El nombre del producto no es valido.");
		validate.isString(product.getVat(), "El iva del producto no es valido.");
		validate.isNumber(product.getVat(), "El iva del producto solo debe tener caracteres numericos.");
		validate.isNumberLess(Integer.parseInt(product.getVat()), 3,
				"El iva del producto es un porcentaje de 0 a 100 no puede ser superior.");
		validate.isString(product.getDiscount(), "El descuento del producto no es valido.");
		validate.isNumber(product.getDiscount(), "El descuento del producto solo debe tener caracteres numericos.");
		validate.isNumberLess(Integer.parseInt(product.getDiscount()), 3,
				"El descuento del producto es un porcentaje de 0 a 100 no puede ser superior.");
		validate.isNumberHigher(product.getPrice(), BigInteger.ZERO, "El precio del producto no es valido.");
		validate.isNumberHigher(product.getStock(), 0, "El stock del producto debe ser mayor o igual a cero.");
		validate.isNumberHigher(product.getStockMin(), 0,
				"El stock minimo del producto debe ser mayor o igual a cero.");
		validate.isLong(product.getCategory().getId(), "La categoria del producto no es valida.");
	}
}
