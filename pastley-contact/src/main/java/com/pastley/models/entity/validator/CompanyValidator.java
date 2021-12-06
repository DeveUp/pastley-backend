package com.pastley.models.entity.validator;

import java.math.BigInteger;

import com.pastley.models.entity.Company;
import com.pastley.util.PastleyModelValidate;
import com.pastley.util.exception.PastleyException;

/**
 * @project Pastley-Contact.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public class CompanyValidator {

	public static void validator(Company company) throws PastleyException {
		PastleyModelValidate validate = new PastleyModelValidate();
		if (company == null)
			throw new PastleyException("No se ha podido validar la empresa.");
		validate.isString(company.getName(), "El nombre del la empresa no es valido.");
		validate.isString(company.getAddress(), "La ubicacion del la empresa no es valida.");
		validate.isString(company.getEmail(), "El email del la empresa no es valido.");
		validate.isString(company.getPassword(), "La clave de la empresa no es valida.");
		validate.isNumberHigher(company.getButdget(), BigInteger.ZERO, "El presupuesto de la empresa no es valido.");
	}
}
