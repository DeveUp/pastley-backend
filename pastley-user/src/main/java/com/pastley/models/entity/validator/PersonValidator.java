package com.pastley.models.entity.validator;

import com.pastley.models.entity.Person;
import com.pastley.util.PastleyModelValidate;
import com.pastley.util.exception.PastleyException;

/**
 * @project Pastley-User.
 * @author Leyner Jose Ortega Arias.
 * @Github https://github.com/leynerjoseoa.
 * @contributors serbuitrago.
 * @version 1.0.0.
 */
public class PersonValidator {
	public static void validator(Person person) throws PastleyException {
		PastleyModelValidate validate = new PastleyModelValidate();
		if (person == null)
			throw new PastleyException("No se ha podido validar la persona.");
		if (person.getTypeDocument() == null)
			throw new PastleyException("No se ha podido validar el tipo documento de la persona.");
		validate.isString(person.getName(), "El nombre de la persona no es valido.");
		validate.isString(person.getSubname(), "El apellido de la persona no es valido.");
		validate.isString(person.getPhone(), "El telefono de la persona no es valido.");
		validate.isNumberLess(person.getPhone().length(), 10,
				"El telefono de la persona no puede tener mas de 10 caracteres.");
		validate.isString(person.getEmail(), "El email de la persona no es valido.");
		validate.isLong(person.getDocument(), "El documento de la persona no es valido.");
		validate.isLong(person.getTypeDocument().getId(), "El tipo de documento de la persona no es valido.");
	}
}
