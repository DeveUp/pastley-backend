package com.pastley.application.validator;

import com.pastley.application.exception.PastleyException;
import com.pastley.domain.Category;

public class CategoryValidator {
	public static void validator(Category category) throws PastleyException {
		PastleyModelValidate validate = new PastleyModelValidate();
		if (category == null)
			throw new PastleyException("No se ha podido validar la categoria.");
		validate.isString(category.getName(), "El nombre del producto no es valido.");
	}
}
