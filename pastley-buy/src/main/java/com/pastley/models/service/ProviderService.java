package com.pastley.models.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pastley.models.entity.Provider;
import com.pastley.models.repository.ProviderRepository;
import com.pastley.util.PastleyDate;
import com.pastley.util.PastleyInterface;
import com.pastley.util.PastleyValidate;
import com.pastley.util.exception.PastleyException;

/**
 * @project Pastley-Buy.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Service
public class ProviderService implements PastleyInterface<Long, Provider> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProviderService.class);

	@Autowired
	ProviderRepository providerRepository;

	@Override
	public Provider findById(Long id) {
		if (id <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del proveedor no es valido.");
		Optional<Provider> provider = providerRepository.findById(id);
		if (!provider.isPresent())
			throw new PastleyException(HttpStatus.NOT_FOUND, "No existe ningun proveedor con el id " + id + ".");
		return provider.orElse(null);
	}
	
	public Provider findByName(String name) {
		if(!PastleyValidate.isChain(name))
			throw new PastleyException(HttpStatus.NOT_FOUND, "El nombre del proveedor no es valido.");
		Provider provider = providerRepository.findByName(name);
		if(provider == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No existe ningun proveedor con el nombre " + name + ".");
		return provider;
	}
	
	@Override
	public List<Provider> findAll() {
		return providerRepository.findAll();
	}
	
	@Override
	public List<Provider> findByStatuAll(boolean statu) {
		return providerRepository.findByStatu(statu);
	}
	
	public List<Provider> findByRangeDateRegister(String start, String end) {
		String arrayDate[] = PastleyValidate.isRangeDateRegisterValidateDate(start, end);
		return providerRepository.findByRangeDateRegister(arrayDate[0], arrayDate[1]);
	}

	@Override
	public Provider save(Provider entity) {
		return null;
	}
	
	public Provider save(Provider entity, int type) {
		if(entity == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha recibido el proveedor.");
		String message = entity.validate();
		String messageType = PastleyValidate.messageToSave(type, false);
		if (message != null)
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha " + messageType + " el proveedor, " + message + ".");
		Provider provider = (entity.getId() != null && entity.getId() > 0) ? saveToUpdate(entity, type)
				: saveToSave(entity, type);
		provider = providerRepository.save(provider);
		if (provider == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " el proveedor.");
		return provider;
	}
	
	@Override
	public boolean delete(Long id) {
		findById(id);
		providerRepository.deleteById(id);
		try {
			if (findById(id) == null) {
				return true;
			}
		}catch (PastleyException e) {
			LOGGER.error("[delete(Long id)]", e);
			return true;
		}
		throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha eliminado el proveedor con el id " + id + ".");
	}
	
	private Provider saveToSave(Provider entity, int type) {
		if (!validateName(entity.getName()))
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"Ya existe un proveedor con el nombre " + entity.getName() + ".");
		PastleyDate date = new PastleyDate();
		entity.uppercase();
		entity.setId(0L);
		entity.setDateRegister(date.currentToDateTime(null));
		entity.setDateUpdate(null);
		entity.setStatu(true);
		return entity;
	}
	
	private Provider saveToUpdate(Provider entity, int type) {
		Provider provider = null;
		if (type != 3)
			provider = findById(entity.getId());
		if (!testName(entity.getName(), (type == 3 ? entity.getName() : provider.getName())))
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"Ya existe un proveedor con el nombre " + entity.getName() + ".");
		PastleyDate date = new PastleyDate();
		entity.uppercase();
		entity.setDateRegister((type == 3) ? entity.getDateRegister() : provider.getDateRegister());
		entity.setDateUpdate(date.currentToDateTime(null));
		entity.setStatu((type == 3) ? !entity.isStatu() : entity.isStatu());
		return entity;
	}
	
	private boolean validateName(String name) {
		Provider provider = null;
		try {
			provider = findByName(name);
		} catch (PastleyException e) {
			LOGGER.error("[validateName(String name)]", e);
		}
		return (provider == null) ? true : false;
	}

	private boolean testName(String nameA, String nameB) {
		return (!nameA.equalsIgnoreCase(nameB)) ? validateName(nameA) : true;
	}
}
