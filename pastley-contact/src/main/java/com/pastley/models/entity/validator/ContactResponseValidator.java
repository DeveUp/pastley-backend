package com.pastley.models.entity.validator;

import com.pastley.models.entity.ContactResponse;
import com.pastley.util.PastleyModelValidate;
import com.pastley.util.exception.PastleyException;

/**
 * @project Pastley-Contact.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public class ContactResponseValidator {
	public static void validator(ContactResponse contactResponse) throws PastleyException {
		PastleyModelValidate validate = new PastleyModelValidate();
		if (contactResponse == null)
			throw new PastleyException("No se ha podido validar la respuesta del contacto.");
		if (contactResponse.getContact() == null)
			throw new PastleyException(
					"No se ha podido validar la respuesta del contacto, el contacto principal no se ha recibido.");
		validate.isString(contactResponse.getResponse(), "El mensaje de respuesta del contacto no es valido.");
		validate.isNumberLess(contactResponse.getResponse().length(), 500,
				"El mensaje de respuesta del contacto no puede tener mas de 500 caracteres.");
		validate.isLong(contactResponse.getContact().getId(), "El contacto principal de la respuesta no es valido.");
	}
}
