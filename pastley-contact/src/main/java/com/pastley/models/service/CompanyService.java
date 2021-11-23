package com.pastley.models.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pastley.models.entity.Company;
import com.pastley.models.repository.CompanyRepository;
import com.pastley.util.PastleyDate;
import com.pastley.util.PastleyInterface;
import com.pastley.util.PastleyValidate;
import com.pastley.util.exception.PastleyException;

/**
 * @project Pastley-Contact.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/serbuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Service
public class CompanyService implements PastleyInterface<Long, Company> {

	@Autowired
	CompanyRepository companyDao;

	@Override
	public Company findById(Long id) {
		if(id <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id de la empresa no es valido.");
		Optional<Company> type = companyDao.findById(id);
		if (!type.isPresent())
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha encontrado ninguna empresa con el id " + id + ".");
		return type.orElse(null);
	}

	@Override
	public List<Company> findAll() {
		return new ArrayList<>();
	}

	@Override
	public List<Company> findByStatuAll(boolean statu) {
		return new ArrayList<>();
	}

	@Override
	public Company save(Company entity) {
		return null;
	}
	
	public Company updateButdget(Long id, BigInteger value) {
		Company company = findById(id);
		if(value == null)
			value = BigInteger.ZERO;
		company.setButdget(company.getButdget().add(value));
		return save(company, 2);
	}

	public Company save(Company entity, int type) {
		if(entity == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha recibido la empresa.");
		String message = entity.validate();
		String messageType = PastleyValidate.messageToSave(type, false);
		if(message != null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " la empresa, " + message + ".");
		Company company = (entity.getId() != null && entity.getId() > 0) ? saveToUpdate(entity, type) : saveToSave(entity, type);
		company = companyDao.save(company);
		if (company == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " la empresa.");
		return company;
	}

	@Override
	public boolean delete(Long id) {
		return false;
	}
	
	private Company saveToSave(Company entity, int type) {
		return entity;
	}

	private Company saveToUpdate(Company entity, int type) {
		Company company = null;
		if(type != 3)
			company = findById(entity.getId());
		PastleyDate date = new PastleyDate();
		entity.uppercase();
		entity.update(company);
		entity.setDateRegister((type == 3) ? entity.getDateRegister(): company.getDateRegister());
		entity.setDateUpdate(date.currentToDateTime(null));
		entity.setStatu((type == 3) ? !entity.isStatu() : entity.isStatu());
		return entity;
	}
}
