package com.pastley.models.service;

import java.math.BigInteger;

import com.pastley.models.entity.Company;

/**
 * @project Pastley-Contact.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/serbuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public interface CompanyService {

	Company findById(Long id);

	Company updateButdget(Long id, BigInteger value);

	Company save(Company entity, int type);
}
