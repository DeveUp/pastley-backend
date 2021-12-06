package com.pastley.models.entity.validator;

import com.pastley.models.entity.TypeDocument;
import com.pastley.util.PastleyModelValidate;
import com.pastley.util.exception.PastleyException;

/**
 * @project Pastley-User.
 * @author Leyner Jose Ortega Arias.
 * @Github https://github.com/leynerjoseoa.
 * @contributors serbuitrago.
 * @version 1.0.0.
 */
public class TypeDocumentValidator {
	public static void validator(TypeDocument typeDocument) throws PastleyException {
		PastleyModelValidate validate = new PastleyModelValidate();
		if (typeDocument == null)
			throw new PastleyException("No se ha podido validar el tipo de documento.");
		validate.isString(typeDocument.getName(), "El nombre del tipo de documento no es valido.");
	}
}
