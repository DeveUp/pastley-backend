package com.pastley.models.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pastley.models.entity.Contact;
import com.pastley.models.repository.ContactRepository;
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
public class ContactService implements PastleyInterface<Long, Contact> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);

	@Autowired
    ContactRepository contactRepository;
	
	@Autowired
    TypePQRService typePQRService;

	@Override
	public Contact findById(Long id) {
		if (id <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del contacto no es valido.");
		Optional<Contact> type = contactRepository.findById(id);
		if (!type.isPresent())
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha encontrado ningun contacto con el id " + id + ".");
		return type.orElse(null);
	}

	@Override
	public List<Contact> findAll() {
		return contactRepository.findAll();
	}
	
	public List<Contact> findByUserAll(Long idUser) {
		if(idUser <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del usuario no es valido.");
		return contactRepository.findByIdUser(idUser);
	}
	
	public List<Contact> findByTypePqrAll(Long idTypePqr) {
		if(idTypePqr <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del tipo de pqr no es valido.");
		return contactRepository.findByIdTypePqr(idTypePqr);
	}

	@Override
	public List<Contact> findByStatuAll(boolean statu) {
		return contactRepository.findByStatu(statu);
	}

	public List<Contact> findByRangeDateRegister(String start, String end) {
		String arrayDate[] = PastleyValidate.isRangeDateRegisterValidateDate(start, end);
		return contactRepository.findByRangeDateRegister(arrayDate[0], arrayDate[1]);
	}

	@Override
	public Contact save(Contact entity) {
		return null;
	}
	
	public Contact save(Contact entity, int type) {
		if(entity == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha recibido el contacto.");
		String message = entity.validate(false);
		String messageType = PastleyValidate.messageToSave(type, false);
		if (message != null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " el contacto, " + message + ".");
		if(type == 1 || type == 2)
			entity.setTypePqr(typePQRService.findById(entity.getTypePqr().getId()));
		Contact contact = (entity.getId() != null && entity.getId() > 0) ? saveToUpdate(entity, type) : saveToSave(entity, type);
		contact = contactRepository.save(contact);
		if (contact == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " el contacto.");
		return contact;
	}
	
	@Override
	public boolean delete(Long id) {
		findById(id);
		contactRepository.deleteById(id);
		try {
			if (findById(id) == null) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.info("[delete]: "+e.getMessage());
			return true;
		}
		throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha eliminado el contacto con el id " + id + ".");
	}
	
	private Contact saveToSave(Contact entity, int type) {
		PastleyDate date = new PastleyDate();
		entity.setId(0L);
		entity.setDateRegister(date.currentToDateTime(null));
		entity.setDateUpdate(null);
		entity.setStatu(true);
		return entity;
	}
	
	private Contact saveToUpdate(Contact entity, int type) {
		Contact contact = null;
		if(type != 3)
			contact = findById(entity.getId());
		PastleyDate date = new PastleyDate();
		entity.setDateRegister((type == 3) ? entity.getDateRegister(): contact.getDateRegister());
		entity.setDateUpdate(date.currentToDateTime(null));
		entity.setStatu((type == 3) ? !entity.isStatu() : entity.isStatu());
		return entity;
	}
}
