package com.pastley.models.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastley.models.entity.Person;
import com.pastley.models.entity.TypeDocument;
import com.pastley.models.entity.validator.TypeDocumentValidator;
import com.pastley.models.repository.TypeDocumentRepository;
import com.pastley.models.service.PersonService;
import com.pastley.models.service.TypeDocumentService;
import com.pastley.util.PastleyDate;
import com.pastley.util.PastleyValidate;
import com.pastley.util.exception.PastleyException;

/**
 * @project Pastley-User.
 * @author Leyner Jose Ortega Arias.
 * @Github https://github.com/leynerjoseoa.
 * @contributors serbuitrago.
 * @version 1.0.0.
 */
@Service
public class TypeDocumentServiceImpl implements TypeDocumentService{

	private static final Logger LOGGER = LoggerFactory.getLogger(TypeDocumentServiceImpl.class);
	
	@Autowired
	TypeDocumentRepository typeDocumentRepository;
	
	@Autowired
	PersonService personService;
	
	@Override
	public TypeDocument findById(Long id) {
		if (!PastleyValidate.isLong(id))
			throw new PastleyException("El tipo de documento no es valido.");
		Optional<TypeDocument> typeDocument = typeDocumentRepository.findById(id);
		if (!typeDocument.isPresent())
			throw new PastleyException("No existe ningun tipo de documento con el id " + id + ".");
		return typeDocument.orElse(null);
	}

	@Override
	public TypeDocument findByName(String name) {
		if (!PastleyValidate.isChain(name))
			throw new PastleyException("El tipo de documento no es valido.");
		TypeDocument typeDocument = typeDocumentRepository.findByName(name);
		if (typeDocument == null)
			throw new PastleyException("No existe ningun tipo de documento con el nombre " + name + ".");
		return typeDocument;
	}

	@Override
	public List<TypeDocument> findAll() {
		return typeDocumentRepository.findAll();
	}

	@Override
	public TypeDocument save(TypeDocument entity, int type) {
		TypeDocumentValidator.validator(entity);
		String messageType = PastleyValidate.messageToSave(type, false);
		TypeDocument typeDocument = PastleyValidate.isLong(entity.getId()) ? saveToUpdate(entity, type)
				: saveToSave(entity, type);
		typeDocument = typeDocumentRepository.save(typeDocument);
		if (typeDocument == null)
			throw new PastleyException("No se ha " + messageType + " el tipo de documento.");
		return typeDocument;
	}

	@Override
	public boolean delete(Long id) {
		findById(id);
		List<Person> list = personService.findByIdTypeDocumentAll(id);
		if (!list.isEmpty())
			throw new PastleyException("No se ha eliminado el tipo de documento con el id  " + id
					+ ", tiene asociado " + list.size() + " personas.");
		typeDocumentRepository.deleteById(id);
		try {
			if (findById(id) == null)
				return true;
		} catch (PastleyException e) {
			LOGGER.error("[delete(Long id)]", e);
			return true;
		}
		throw new PastleyException("No se ha eliminado el tipo de documento con el id " + id + ".");
	}
	
	private TypeDocument saveToSave(TypeDocument entity, int type) {
		if (!validateName(entity.getName()))
			throw new PastleyException("Ya existe un tipo de documento con el nombre " + entity.getName() + ".");
		PastleyDate date = new PastleyDate();
		uppercase(entity);
		entity.setId(0L);
		entity.setDateRegister(date.currentToDateTime(null));
		entity.setDateUpdate(null);
		entity.setStatu(true);
		return entity;
	}

	private TypeDocument saveToUpdate(TypeDocument entity, int type) {
		TypeDocument typeDocument = null;
		if (type == 2) {
			typeDocument = findById(entity.getId());
			if (!testName(entity.getName(), typeDocument.getName()))
				throw new PastleyException("Ya existe un tipo de documento con el nombre " + entity.getName() + ".");
		}
		PastleyDate date = new PastleyDate();
		uppercase(entity);
		entity.setDateRegister((type == 2) ?  typeDocument.getDateRegister() : entity.getDateRegister());
		entity.setDateUpdate(date.currentToDateTime(null));
		entity.setStatu((type == 3) ? !entity.isStatu() :  type == 2 ? typeDocument.isStatu() : entity.isStatu());
		return entity;
	}

	private boolean validateName(String name) {
		TypeDocument typeDocument = null;
		try {
			typeDocument = findByName(name);
		} catch (PastleyException e) {
			LOGGER.error("[validateName(String name)]", e);
		}
		return (typeDocument == null) ? true : false;
	}

	private boolean testName(String nameA, String nameB) {
		return (!nameA.equalsIgnoreCase(nameB)) ? validateName(nameA) : true;
	}
	
	private void uppercase(TypeDocument typeDocument) {
		typeDocument.setName(PastleyValidate.uppercase(typeDocument.getName()));
	}
}
