package com.pastley.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pastley.application.repository.PersonRepository;
import com.pastley.domain.Person;
import com.pastley.infrastructure.config.PastleyDate;
import com.pastley.infrastructure.config.PastleyInterface;
import com.pastley.infrastructure.config.PastleyValidate;
import com.pastley.infrastructure.exception.PastleyException;

/**
 * @project Pastley-User.
 * @author Leyner Jose Ortega Arias.
 * @Github https://github.com/leynerjoseoa.
 * @contributors serbuitrago.
 * @version 1.0.0.
 */
@Service
public class PersonService implements PastleyInterface<Long, Person> {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

	@Autowired
	PersonRepository personRepository;
	@Autowired
	UserService userService;
	@Autowired
	TypeDocumentService typeDocumentService;

	@Override
	public Person findById(Long id) {
		if (id <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id de la persona no es valido.");
		Optional<Person> person = personRepository.findById(id);
		if (!person.isPresent())
			throw new PastleyException(HttpStatus.NOT_FOUND, "No existe ninguna persona con el id " + id + ".");
		return person.orElse(null);
	}

	public Person findByEmail(String email) {
		if (!PastleyValidate.isChain(email))
			throw new PastleyException(HttpStatus.NOT_FOUND, "El email de la persona no es valido.");
		Person person = personRepository.findByEmail(email);
		if (person == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No existe ninguna persona con el email " + email + ".");
		return person;
	}

	public Person findByDocument(Long document) {
		if (document == null || document <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El documento de la persona no es valido.");
		Person person = personRepository.findByDocument(document);
		if (person == null)
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No existe ninguna persona con el documento " + document + ".");
		return person;
	}

	@Override
	public List<Person> findAll() {
		return personRepository.findAll();
	}

	@Override
	public List<Person> findByStatuAll(boolean statu) {
		return new ArrayList<>();
	}

	public List<Person> findByIdTypeDocumentAll(Long idTypeDocument) {
		if (idTypeDocument <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El tipo de documento de la persona no es valido.");
		return personRepository.findByIdTypeDocument(idTypeDocument);
	}

	@Override
	public Person save(Person entity) {
		return null;
	}

	public Person save(Person entity, int type) {
		if (entity == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha recibido la persona.");
		String message = entity.validate();
		String messageType = PastleyValidate.messageToSave(type, false);
		if (message != null)
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha " + messageType + " la persona, " + message + ".");
		Person person = (entity.getId() != null && entity.getId() > 0) ? saveToUpdate(entity, type)
				: saveToSave(entity, type);
		if (type == 2 || type == 1) {
			person.setTypeDocument(typeDocumentService.findById(entity.getId()));
		}
		person = personRepository.save(person);
		if (person == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " la persona.");
		return person;
	}
	
	@Override
	public boolean delete(Long id) {
		Person person = findById(id);
		if (testDocumentUser(person.getDocument()))
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha eliminado la persona el id " + id + ", esta asociado a un usuario.");
		personRepository.existsById(id);
		try {
			if (findById(id) == null) {
				return true;
			}
		} catch (PastleyException e) {
			LOGGER.error("[delete(Long id)]", e);
			return true;
		}
		throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha eliminado la persona el id " + id + ".");
	}

	private Person saveToSave(Person entity, int type) {
		if (!validateDocument(entity.getDocument()))
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"Ya existe una persona con el documento " + entity.getDocument() + ".");
		if (!validateEmail(entity.getEmail()))
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"Ya existe una persona con el email " + entity.getEmail() + ".");
		PastleyDate date = new PastleyDate();
		entity.uppercase();
		entity.setId(0L);
		entity.setDateRegister(date.currentToDateTime(null));
		entity.setDateUpdate(null);
		return entity;
	}

	private Person saveToUpdate(Person entity, int type) {
		Person person = null;
		if (type != 3) {
			person = findById(entity.getId());
			if (person == null)
				throw new PastleyException(HttpStatus.NOT_FOUND,
						"No se ha encontrado persona con el id " + entity.getId() + ".");
			if (!testDocument(entity.getDocument(), person.getDocument()))
				throw new PastleyException(HttpStatus.NOT_FOUND,
						"Ya existe una persona con el documento " + entity.getDocument() + ".");
			if (!testEmail(entity.getEmail(), person.getEmail()))
				throw new PastleyException(HttpStatus.NOT_FOUND,
						"Ya existe una persona con el email " + entity.getEmail() + ".");
		}
		PastleyDate date = new PastleyDate();
		entity.uppercase();
		entity.setDateRegister((type != 3) ? person.getDateRegister() : entity.getDateRegister());
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

	private boolean testDocument(Long documentA, long documentB) {
		return documentA != documentB ? validateDocument(documentA) : true;
	}

	private boolean testEmail(String emailA, String emailB) {
		return !emailA.equalsIgnoreCase(emailB) ? validateEmail(emailA) : true;
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
}
