package com.pastley.models.entity.validator;

import java.math.BigInteger;

import com.pastley.models.entity.Cart;
import com.pastley.util.PastleyModelValidate;
import com.pastley.util.exception.PastleyException;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public class CartValidator {
	public static void validator(Cart cart) throws PastleyException {
		PastleyModelValidate validate = new PastleyModelValidate();
		if (cart == null)
			throw new PastleyException("No se ha podido validar producto en el carrito.");
		validate.isLong(cart.getDocumentCustomer(), "El documento del cliente no es valido.");
		validate.isLong(cart.getIdProduct(), "El id del producto no es valido.");
		validate.isNumberHigher(cart.getPrice(), BigInteger.ZERO, "El precio del producto no es valido.");
		validate.isNumberHigher(cart.getCount(), 0, "La cantidad del producto debe ser mayor a cero.");
	}
}
