package com.pastley.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pastley.application.repository.TypeDocumentRepository;
import com.pastley.domain.Person;
import com.pastley.domain.TypeDocument;
import com.pastley.infrastructure.config.PastleyDate;
import com.pastley.infrastructure.config.PastleyInterface;
import com.pastley.infrastructure.config.PastleyValidate;
import com.pastley.infrastructure.exception.PastleyException;

/**
 * @project Pastley-User.
 * @author Leyner Jose Ortega Arias.
 * @Github https://github.com/leynerjoseoa.
 * @contributors soleimygomez, serbuitrago, jhonatanbeltran.
 * @version 1.0.0.
 */
@Service
public class TypeDocumentService implements PastleyInterface<Long, TypeDocument> {

	private static final Logger LOGGER = LoggerFactory.getLogger(TypeDocument.class);

	@Autowired
	private TypeDocumentRepository typeDocumentDAO;

	@Autowired
	private PersonService personService;

	@Override
	public TypeDocument findById(Long id) {
		if (id <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El tipo de documento no es valido.");
		Optional<TypeDocument> typeDocument = typeDocumentDAO.findById(id);
		if (!typeDocument.isPresent())
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No existe ningun tipo de documento con el id " + id + ".");
		return typeDocument.orElse(null);
	}

	public TypeDocument findByName(String name) {
		if (!PastleyValidate.isChain(name))
			throw new PastleyException(HttpStatus.NOT_FOUND, "EL tipo de documento no es valido.");
		TypeDocument typeDocument = typeDocumentDAO.findByName(name);
		if (typeDocument == null)
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No existe ningun tipo de documento con el nombre " + name + ".");
		return typeDocument;
	}

	@Override
	public List<TypeDocument> findAll() {
		return typeDocumentDAO.findAll();
	}

	@Override
	public List<TypeDocument> findByStatuAll(boolean statu) {
		return new ArrayList<>();
	}

	@Override
	public TypeDocument save(TypeDocument entity) {
		return null;
	}

	public TypeDocument save(TypeDocument entity, int type) {
		if (entity == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha recibido el tipo de documento.");
		String message = entity.validate();
		String messageType = PastleyValidate.messageToSave(type, false);
		if (message != null)
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha " + messageType + " el tipo de documento, " + message + ".");
		TypeDocument typeDocument = (entity.getId() != null && entity.getId() > 0) ? saveToUpdate(entity, type)
				: saveToSave(entity, type);
		typeDocument = typeDocumentDAO.save(typeDocument);
		if (typeDocument == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " el tipo de documento.");
		return typeDocument;
	}

	@Override
	public boolean delete(Long id) {
		findById(id);
		List<Person> list = personService.findByIdTypeDocumentAll(id);
		if (!list.isEmpty())
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha eliminado el tipo de documento con el id  " + id
					+ ", tiene asociado " + list.size() + " personas.");
		typeDocumentDAO.deleteById(id);
		try {
			if (findById(id) == null) {
				return true;
			}
		} catch (PastleyException e) {
			LOGGER.error("[delete(Long id)]", e);
			return true;
		}
		throw new PastleyException(HttpStatus.NOT_FOUND,
				"No se ha eliminado el tipo de documento con el id " + id + ".");
	}

	private TypeDocument saveToSave(TypeDocument entity, int type) {
		if (!validateName(entity.getName()))
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"Ya existe un tipo dedocumento con el nombre " + entity.getName() + ".");
		PastleyDate date = new PastleyDate();
		entity.uppercase();
		entity.setId(0L);
		entity.setDateRegister(date.currentToDateTime(null));
		entity.setDateUpdate(null);
		entity.setStatu(true);
		return entity;
	}

	private TypeDocument saveToUpdate(TypeDocument entity, int type) {
		TypeDocument method = null;
		if (type != 3)
			method = findById(entity.getId());
		if (!testName(entity.getName(), (type == 3 ? entity.getName() : method.getName())))
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"Ya existe un tipo de documento con el nombre " + entity.getName() + ".");
		PastleyDate date = new PastleyDate();
		entity.uppercase();
		entity.setDateRegister((type == 3) ? entity.getDateRegister() : method.getDateRegister());
		entity.setDateUpdate(date.currentToDateTime(null));
		entity.setStatu((type == 3) ? !entity.isStatu() : entity.isStatu());
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
}