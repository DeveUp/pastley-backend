package com.pastley.models.entity.validator;

import com.pastley.models.entity.Contact;
import com.pastley.util.PastleyModelValidate;
import com.pastley.util.exception.PastleyException;

/**
 * @project Pastley-Contact.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public class ContactValidator {
	public static void validator(Contact contact) throws PastleyException {
		PastleyModelValidate validate = new PastleyModelValidate();
		if (contact == null)
			throw new PastleyException("No se ha podido validar el contacto.");
		if (contact.getTypePqr() == null)
			throw new PastleyException("No se ha podido validar el tipo de pqr del contacto.");
		validate.isString(contact.getMessage(), "El mensaje del contacto no es valido.");
		validate.isNumberLess(contact.getMessage().length(), 500,
				"El mensaje del contacto no puede tener mas de 500 caracteres.");
		validate.isString(contact.getName(), "El nombre de la persona del contacto no es valido.");
		validate.isString(contact.getEmail(), "El email de la persona del contacto no es valido.");
		validate.isLong(contact.getDocument(), "El documento de la persona del contacto no es valido.");
		validate.isLong(contact.getTypePqr().getId(), "El tipo de pqr del contacto no es valido.");
	}
}
