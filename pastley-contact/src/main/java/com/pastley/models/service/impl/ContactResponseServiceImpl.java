package com.pastley.models.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastley.models.entity.ContactResponse;
import com.pastley.models.entity.validator.ContactResponseValidator;
import com.pastley.models.repository.ContactResponseRepository;
import com.pastley.models.service.ContactResponseService;
import com.pastley.models.service.ContactService;
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
public class ContactResponseServiceImpl implements ContactResponseService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContactResponseService.class);

	@Autowired
	ContactResponseRepository contactResponseRepository;

	@Autowired
	ContactService contactService;

	@Override
	public ContactResponse findById(Long id) {
		if (!PastleyValidate.isLong(id))
			throw new PastleyException("El id de la respuesta del contacto no es valido.");
		Optional<ContactResponse> type = contactResponseRepository.findById(id);
		if (!type.isPresent())
			throw new PastleyException("No se ha encontrado ninguna respuesta del contacto con el id " + id + ".");
		return type.orElse(null);
	}

	@Override
	public List<ContactResponse> findAll() {
		return contactResponseRepository.findAll();
	}

	public List<ContactResponse> findByContactAll(Long idContact) {
		if (!PastleyValidate.isLong(idContact))
			throw new PastleyException("El id del contacto no es valido.");
		return contactResponseRepository.findByIdContact(idContact);
	}

	public List<ContactResponse> findByRangeDateRegister(String start, String end) {
		String[] arrayDate = PastleyValidate.isRangeDateRegisterValidateDate(start, end);
		return contactResponseRepository.findByRangeDateRegister(arrayDate[0], arrayDate[1]);
	}

	@Override
	public ContactResponse save(ContactResponse entity) {
		ContactResponseValidator.validator(entity);
		String messageType = PastleyValidate.messageToSave((PastleyValidate.isLong(entity.getId()) ? 2 : 1), false);
		entity.setContact(contactService.findById(entity.getContact().getId()));
		ContactResponse contactResponse = PastleyValidate.isLong(entity.getId()) ? saveToUpdate(entity)
				: saveToSave(entity);
		if (contactResponse == null)
			throw new PastleyException("No se ha " + messageType + " la respuesta del contacto.");
		return contactResponse;
	}

	@Override
	public boolean delete(Long id) {
		findById(id);
		contactResponseRepository.deleteById(id);
		try {
			if (findById(id) == null)
				return true;
		} catch (Exception e) {
			LOGGER.info("[delete(Long id)]", e);
			return true;
		}
		throw new PastleyException("No se ha eliminado la respuesta del contacto con el id " + id + ".");
	}

	private ContactResponse saveToSave(ContactResponse entity) {
		PastleyDate date = new PastleyDate();
		entity.setId(0L);
		entity.setDateRegister(date.currentToDateTime(null));
		entity.setDateUpdate(null);
		return entity;
	}

	private ContactResponse saveToUpdate(ContactResponse entity) {
		ContactResponse contactResponse = findById(entity.getId());
		PastleyDate date = new PastleyDate();
		entity.setDateRegister(contactResponse.getDateRegister());
		entity.setDateUpdate(date.currentToDateTime(null));
		return entity;
	}
}
