package com.pastley.models.entity.validator;

import com.pastley.models.entity.TypePQR;
import com.pastley.util.PastleyModelValidate;
import com.pastley.util.exception.PastleyException;

/**
 * @project Pastley-Contact.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public class TypePQRValidator {
	public static void validator(TypePQR typePQR) throws PastleyException {
		PastleyModelValidate validate = new PastleyModelValidate();
		if (typePQR == null)
			throw new PastleyException("No se ha podido validar el tipo de pqr.");
		validate.isString(typePQR.getName(), "El nombre del tipo pqr no es valido.");
	}
}
