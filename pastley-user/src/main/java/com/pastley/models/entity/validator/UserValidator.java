package com.pastley.models.entity.validator;

import com.pastley.models.entity.User;
import com.pastley.util.PastleyModelValidate;
import com.pastley.util.exception.PastleyException;

/**
 * @project Pastley-User.
 * @author Leyner Jose Ortega Arias.
 * @Github https://github.com/leynerjoseoa.
 * @contributors serbuitrago.
 * @version 1.0.0.
 */
public class UserValidator {

	private static PastleyModelValidate validate = new PastleyModelValidate();

	public static void validator(User user) throws PastleyException {
		if (user == null)
			throw new PastleyException("No se ha podido validar el usuario.");
		if (user.getPerson() == null)
			throw new PastleyException("No se ha podido validar la persona del usuario.");
		validate.isString(user.getNickname(), "El apodo del usuario no es valido.");
		validate.isString(user.getPassword(), "La clave del usuario no es valida.");
	}

	public static void validatorStrict(User user) throws PastleyException {
		validator(user);
		if (user.getRole() == null)
			throw new PastleyException("No se ha podido validar el rol del usuario.");
		validate.isLong(user.getPerson().getId(), "La persona del usuario no es valida.");
		validate.isLong(user.getRole().getId(), "El rol del usuario no es valido.");
	}
}
