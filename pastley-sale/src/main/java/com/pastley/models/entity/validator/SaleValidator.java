package com.pastley.models.entity.validator;

import com.pastley.models.entity.Sale;
import com.pastley.util.PastleyModelValidate;
import com.pastley.util.exception.PastleyException;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public class SaleValidator {
	public static void validator(Sale sale)throws PastleyException {
		PastleyModelValidate validate = new PastleyModelValidate();
		if (sale == null)
			throw new PastleyException("No se ha podido validar la venta.");
		if(sale.getMethodPay() == null)
			throw new PastleyException("No se ha podido validar el metodo de pago de la venta.");
		validate.isLong(sale.getIdCoustomer(), "El id del cliente no es valido.");
		validate.isLong(sale.getMethodPay().getId(), "El id del metodo de pago no es valido.");
		validate.isString(sale.getVat(), "El iva de la venta no es valido.");
		validate.isNumber(sale.getVat(), "El iva de la venta solo debe tener caracteres numericos.");
		validate.isNumberLess(Integer.parseInt(sale.getVat()), 100,
				"El iva de la venta es un porcentaje de 0 a 100 no puede ser superior.");
	}
}
