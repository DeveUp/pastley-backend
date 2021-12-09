package com.pastley.models.entity.validator;

import com.pastley.models.entity.SaleDetail;
import com.pastley.util.PastleyModelValidate;
import com.pastley.util.exception.PastleyException;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public class SaleDetailtValidator {
	public static void validator(SaleDetail saleDetail) throws PastleyException {
		PastleyModelValidate validate = new PastleyModelValidate();
		if (saleDetail == null)
			throw new PastleyException("No se ha podido validar el detalle de venta.");
		if(saleDetail.getCart() == null)
			throw new PastleyException("No se ha podido validar producto del carrito.");
		validate.isLong(saleDetail.getIdSale(), "El id de la venta no es valida.");
		validate.isLong(saleDetail.getCart().getId(), "El id del producto carrito no es valido.");
	}
}
