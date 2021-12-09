package com.pastley.models.entity.validator;

import com.pastley.models.entity.MethodPay;
import com.pastley.util.PastleyModelValidate;
import com.pastley.util.exception.PastleyException;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public class MethodPayValidator {
	public static void validator(MethodPay methodPay) throws PastleyException {
		PastleyModelValidate validate = new PastleyModelValidate();
		if (methodPay == null)
			throw new PastleyException("No se ha podido validar el metodo de pago.");
		validate.isString(methodPay.getName(), "El nombre del metodo de pago no es valido.");
	}
}
