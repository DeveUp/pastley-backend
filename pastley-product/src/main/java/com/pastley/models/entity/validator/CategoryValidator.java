package com.pastley.models.entity.validator;

import com.pastley.models.entity.Category;
import com.pastley.util.PastleyModelValidate;
import com.pastley.util.exception.PastleyException;

/**
 * @project Pastley-Product.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public class CategoryValidator {
	public static void validator(Category category) throws PastleyException {
		PastleyModelValidate validate = new PastleyModelValidate();
		if (category == null)
			throw new PastleyException("No se ha podido validar la categoria.");
		validate.isString(category.getName(), "El nombre del producto no es valido.");
	}
}
