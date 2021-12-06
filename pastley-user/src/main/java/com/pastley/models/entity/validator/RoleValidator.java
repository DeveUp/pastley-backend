package com.pastley.models.entity.validator;

import com.pastley.models.entity.Role;
import com.pastley.util.PastleyModelValidate;
import com.pastley.util.exception.PastleyException;

/**
 * @project Pastley-User.
 * @author Leyner Jose Ortega Arias.
 * @Github https://github.com/leynerjoseoa.
 * @contributors serbuitrago.
 * @version 1.0.0.
 */
public class RoleValidator {
	public static void validator(Role role) throws PastleyException {
		PastleyModelValidate validate = new PastleyModelValidate();
		if (role == null)
			throw new PastleyException("No se ha podido validar el rol.");
		validate.isString(role.getName(), "El nombre del rol no es valido.");
	}
}
