package com.pastley.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pastley.application.repository.ContactResponseRepository;
import com.pastley.domain.ContactResponse;
import com.pastley.infrastructure.config.PastleyDate;
import com.pastley.infrastructure.config.PastleyInterface;
import com.pastley.infrastructure.config.PastleyValidate;
import com.pastley.infrastructure.exception.PastleyException;

/**
 * @project Pastley-Contact.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/serbuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Service
public class ContactResponseService implements PastleyInterface<Long, ContactResponse> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ContactResponseService.class);

	@Autowired
	ContactResponseRepository contactResponseRepository;
	
	@Autowired
    ContactService contactService;

	@Override
	public ContactResponse findById(Long id) {
		if (id <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id de la respuesta del contacto no es valido.");
		Optional<ContactResponse> type = contactResponseRepository.findById(id);
		if (!type.isPresent())
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha encontrado ninguna respuesta del contacto con el id " + id + ".");
		return type.orElse(null);
	}

	@Override
	public List<ContactResponse> findAll() {
		return contactResponseRepository.findAll();
	}

	public List<ContactResponse> findByContactAll(Long idContact) {
		if (idContact <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del contacto no es valido.");
		return contactResponseRepository.findByIdContact(idContact);
	}

	public List<ContactResponse> findByRangeDateRegister(String start, String end) {
		String [] arrayDate = PastleyValidate.isRangeDateRegisterValidateDate(start, end);
		return contactResponseRepository.findByRangeDateRegister(arrayDate[0], arrayDate[1]);
	}

	@Override
	public List<ContactResponse> findByStatuAll(boolean statu) {
		return new ArrayList<>();
	}

	@Override
	public ContactResponse save(ContactResponse entity) {
		if(entity == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha recibido la respuesta del contacto.");
		String message = entity.validate();
		if (message != null)
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"Se ha presentado un error en la respuesta de contacto, " + message + ".");
		String messageType = (entity.getId() != null && entity.getId() > 0) ? "actualizar" : "registrar";
		entity.setContact(contactService.findById(entity.getContact().getId()));
		ContactResponse contactResponse = (entity.getId() != null && entity.getId() > 0) ? saveToUpdate(entity)
				: saveToSave(entity);
		if (contactResponse == null)
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha " + messageType + " la respuesta del contacto.");
		return contactResponse;
	}

	@Override
	public boolean delete(Long id) {
		findById(id);
		contactResponseRepository.deleteById(id);
		try {
			if (findById(id) == null) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.info("[delete(Long id)]", e);
			return true;
		}
		throw new PastleyException(HttpStatus.NOT_FOUND,
				"No se ha eliminado la respuesta del contacto con el id " + id + ".");
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
