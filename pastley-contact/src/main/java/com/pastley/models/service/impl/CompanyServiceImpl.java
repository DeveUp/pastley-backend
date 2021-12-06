package com.pastley.models.service.impl;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastley.models.entity.Company;
import com.pastley.models.entity.validator.CompanyValidator;
import com.pastley.models.repository.CompanyRepository;
import com.pastley.models.service.CompanyService;
import com.pastley.util.PastleyDate;
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
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	CompanyRepository companyRepository;

	@Override
	public Company findById(Long id) {
		if (!PastleyValidate.isLong(id))
			throw new PastleyException("El id de la empresa no es valido.");
		Optional<Company> type = companyRepository.findById(id);
		if (!type.isPresent())
			throw new PastleyException("No se ha encontrado ninguna empresa con el id " + id + ".");
		return type.orElse(null);
	}

	@Override
	public Company updateButdget(Long id, BigInteger value) {
		Company company = findById(id);
		if (value == null)
			value = BigInteger.ZERO;
		company.setButdget(company.getButdget().add(value));
		return save(company, 2);
	}

	public Company save(Company entity, int type) {
		CompanyValidator.validator(entity);
		String messageType = PastleyValidate.messageToSave(type, false);
		Company company = (PastleyValidate.isLong(entity.getId())) ? saveToUpdate(entity, type)
				: saveToSave(entity, type);
		company = companyRepository.save(company);
		if (company == null)
			throw new PastleyException("No se ha " + messageType + " la empresa.");
		return company;
	}

	private Company saveToSave(Company entity, int type) {
		PastleyDate date = new PastleyDate();
		entity.setId(0L);
		entity.setStatu(true);
		entity.setDateRegister(date.currentToDateTime(null));
		entity.setDateUpdate(null);
		return entity;
	}

	private Company saveToUpdate(Company entity, int type) {
		Company company = null;
		if (type != 3)
			company = findById(entity.getId());
		PastleyDate date = new PastleyDate();
		uppercase(entity);
		update(entity, company);
		entity.setDateRegister((type == 3) ? entity.getDateRegister() : company.getDateRegister());
		entity.setDateUpdate(date.currentToDateTime(null));
		entity.setStatu((type == 3) ? !entity.isStatu() : entity.isStatu());
		return entity;
	}

	private void update(Company a, Company b) {
		if (b == null)
			return;
		a.setDesciption(testInfo(a.getDesciption(), b.getDesciption()));
		a.setMission(testInfo(a.getMission(), b.getMission()));
		a.setVision(testInfo(a.getVision(), b.getVision()));
		a.setAboutUs(testInfo(a.getAboutUs(), b.getAboutUs()));
		a.setLogo(testInfo(a.getLogo(), b.getLogo()));
	}

	private void uppercase(Company company) {
		company.setName(PastleyValidate.uppercase(company.getName()));
	}

	private String testInfo(String a, String b) {
		return PastleyValidate.isChain(a) ? a : PastleyValidate.isChain(b) ? b : a;
	}
}
