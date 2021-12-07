package com.pastley.models.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastley.models.entity.Person;
import com.pastley.models.entity.validator.PersonValidator;
import com.pastley.models.repository.PersonRepository;
import com.pastley.models.service.PersonService;
import com.pastley.models.service.TypeDocumentService;
import com.pastley.models.service.UserService;
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
public class PersonServiceImpl implements PersonService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);

	@Autowired
	PersonRepository personRepository;
	@Autowired
	UserService userService;
	@Autowired
	TypeDocumentService typeDocumentService;

	@Override
	public Person findById(Long id) {
		if (!PastleyValidate.isLong(id))
			throw new PastleyException("El id de la persona no es valido.");
		Optional<Person> person = personRepository.findById(id);
		if (!person.isPresent())
			throw new PastleyException("No existe ninguna persona con el id " + id + ".");
		return person.orElse(null);
	}
	
	@Override
	public Person findByPhone(String phone) {
		if (!PastleyValidate.isChain(phone))
			throw new PastleyException("El telefono de la persona no es valido.");
		if(phone.length() > 10)
			throw new PastleyException("El telefono de la persona no puede ser mayor de 10 caracteres.");
		Person person = personRepository.findByPhone(phone);
		if (person == null)
			throw new PastleyException("No existe ninguna persona con el telefono " + phone + ".");
		return person;
	}

	@Override
	public Person findByEmail(String email) {
		if (!PastleyValidate.isChain(email))
			throw new PastleyException("El email de la persona no es valido.");
		if (!PastleyValidate.isEmail(email))
			throw new PastleyException("El email de la persona no cumple el formato.");
		Person person = personRepository.findByEmail(email);
		if (person == null)
			throw new PastleyException("No existe ninguna persona con el email " + email + ".");
		return person;
	}

	@Override
	public Person findByDocument(Long document) {
		if (!PastleyValidate.isLong(document))
			throw new PastleyException("El documento de la persona no es valido.");
		Person person = personRepository.findByDocument(document);
		if (person == null)
			throw new PastleyException("No existe ninguna persona con el documento " + document + ".");
		return person;
	}

	@Override
	public List<Person> findAll() {
		return personRepository.findAll();
	}

	@Override
	public List<Person> findByIdTypeDocumentAll(Long idTypeDocument) {
		if (!PastleyValidate.isLong(idTypeDocument))
			throw new PastleyException("El tipo de documento no es valido.");
		return personRepository.findByIdTypeDocument(idTypeDocument);
	}

	@Override
	public Person saveOrUpdate(Person entity) {
		PersonValidator.validator(entity);
		Person person = null;
		boolean error = false;
		try {
			person = findByDocument(entity.getDocument());
			entity.setId(person.getId());
			error = true;
			person = save(entity, 2);
		} catch (PastleyException e) {
			LOGGER.info("[saveOrUpdate(Person entity)]", e);
			if(!error)
				person= save(entity, 1);
			else
				throw new PastleyException(e.getMessage());
		}
		return person;
	}

	@Override
	public Person save(Person entity, int type) {
		PersonValidator.validator(entity);
		String messageType = PastleyValidate.messageToSave(type, false);
		if (type == 1 || type == 2)
			entity.setTypeDocument(typeDocumentService.findById(entity.getTypeDocument().getId()));
		Person person = (PastleyValidate.isLong(entity.getId())) ? saveToUpdate(entity, type)
				: saveToSave(entity, type);
		person = personRepository.save(person);
		if (person == null)
			throw new PastleyException("No se ha " + messageType + " la persona.");
		return person;
	}

	@Override
	public boolean delete(Long id) {
		Person person = findById(id);
		if (testDocumentUser(person.getDocument()))
			throw new PastleyException(
					"No se ha eliminado la persona con el id " + id + ", esta asociado a un usuario.");
		personRepository.deleteById(id);
		try {
			if (findById(id) == null)
				return true;
		} catch (PastleyException e) {
			LOGGER.error("[delete(Long id)]", e);
			return true;
		}
		throw new PastleyException("No se ha eliminado la persona con el id " + id + ".");
	}

	private Person saveToSave(Person entity, int type) {
		if (!validateDocument(entity.getDocument()))
			throw new PastleyException("Ya existe una persona con el documento " + entity.getDocument() + ".");
		if (!validateEmail(entity.getEmail()))
			throw new PastleyException("Ya existe una persona con el email " + entity.getEmail() + ".");
		if (!validatePhone(entity.getPhone()))
			throw new PastleyException("Ya existe una persona con el telefono " + entity.getPhone() + ".");
		PastleyDate date = new PastleyDate();
		uppercase(entity);
		entity.setId(0L);
		entity.setDateRegister(date.currentToDateTime(null));
		if (!PastleyValidate.isChain(entity.getDateBirthday()))
			entity.setDateBirthday(entity.getDateRegister());
		entity.setDateUpdate(null);
		return entity;
	}

	private Person saveToUpdate(Person entity, int type) {
		Person person = null;
		if (type == 2) {
			person = findById(entity.getId());
			if (!testDocument(entity.getDocument(), person.getDocument()))
				throw new PastleyException("Ya existe una persona con el documento " + entity.getDocument() + ".");
			if (!testEmail(entity.getEmail(), person.getEmail()))
				throw new PastleyException("Ya existe una persona con el email " + entity.getEmail() + ".");
			if (!testPhone(entity.getPhone(), person.getPhone()))
				throw new PastleyException("Ya existe una persona con el telefono " + entity.getPhone() + ".");
		}
		PastleyDate date = new PastleyDate();
		uppercase(person);
		entity.setDateRegister((type == 2) ? person.getDateRegister() : entity.getDateRegister());
		entity.setDateUpdate(date.currentToDateTime(null));
		return entity;
	}

	private boolean validateDocument(Long document) {
		Person person = null;
		try {
			person = findByDocument(document);
		} catch (PastleyException e) {
			LOGGER.error("[validateDocument(Long document)]", e);
		}
		return (person == null) ? true : false;
	}

	private boolean validateEmail(String email) {
		Person person = null;
		try {
			person = findByEmail(email);
		} catch (PastleyException e) {
			LOGGER.error("[validateEmail(String email)]", e);
		}
		return (person == null) ? true : false;
	}
	
	private boolean validatePhone(String phone) {
		Person person = null;
		try {
			person = findByPhone(phone);
		} catch (PastleyException e) {
			LOGGER.error("[validatePhone(String phone)]", e);
		}
		return (person == null) ? true : false;
	}

	private boolean testDocument(Long documentA, long documentB) {
		return documentA != documentB ? validateDocument(documentA) : true;
	}

	private boolean testEmail(String emailA, String emailB) {
		return !emailA.equalsIgnoreCase(emailB) ? validateEmail(emailA) : true;
	}
	
	private boolean testPhone(String phoneA, String phoneB) {
		return !phoneA.equalsIgnoreCase(phoneB) ? validatePhone(phoneA) : true;
	}

	private boolean testDocumentUser(Long document) {
		boolean isUser = false;
		try {
			isUser = userService.findByDocumentPerson(document) != null;
		} catch (Exception e) {
			LOGGER.error("[delete(Long id)]", e);
			isUser = false;
		}
		return isUser;
	}

	private void uppercase(Person person) {
		person.setName(PastleyValidate.uppercase(person.getName()));
		person.setSubname(PastleyValidate.uppercase(person.getSubname()));
		person.setAddress(PastleyValidate.uppercase(person.getAddress()));
	}
}
