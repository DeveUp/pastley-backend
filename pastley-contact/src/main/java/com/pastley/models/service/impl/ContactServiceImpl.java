package com.pastley.models.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastley.models.entity.Contact;
import com.pastley.models.entity.validator.ContactValidator;
import com.pastley.models.repository.ContactRepository;
import com.pastley.models.service.ContactService;
import com.pastley.models.service.TypePQRService;
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
public class ContactServiceImpl implements ContactService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);

	@Autowired
    ContactRepository contactRepository;
	
	@Autowired
    TypePQRService typePQRService;

	@Override
	public Contact findById(Long id) {
		if (!PastleyValidate.isLong(id))
			throw new PastleyException("El id del contacto no es valido.");
		Optional<Contact> type = contactRepository.findById(id);
		if (!type.isPresent())
			throw new PastleyException("No se ha encontrado ningun contacto con el id " + id + ".");
		return type.orElse(null);
	}

	@Override
	public List<Contact> findAll() {
		return contactRepository.findAll();
	}
	
	public List<Contact> findByUserAll(Long idUser) {
		if(!PastleyValidate.isLong(idUser))
			throw new PastleyException("El id del usuario no es valido.");
		return contactRepository.findByIdUser(idUser);
	}
	
	public List<Contact> findByTypePqrAll(Long idTypePqr) {
		if(!PastleyValidate.isLong(idTypePqr))
			throw new PastleyException("El id del tipo de pqr no es valido.");
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
	
	public Contact save(Contact entity, int type) {
		ContactValidator.validator(entity);
		String messageType = PastleyValidate.messageToSave(type, false);
		if(type == 1 || type == 2)
			entity.setTypePqr(typePQRService.findById(entity.getTypePqr().getId()));
		Contact contact = (PastleyValidate.isLong(entity.getId())) ? saveToUpdate(entity, type) : saveToSave(entity, type);
		contact = contactRepository.save(contact);
		if (contact == null)
			throw new PastleyException("No se ha " + messageType + " el contacto.");
		return contact;
	}
	
	@Override
	public boolean delete(Long id) {
		findById(id);
		contactRepository.deleteById(id);
		try {
			if (findById(id) == null)
				return true;
		} catch (Exception e) {
			LOGGER.info("[delete(Long id)]", e);
			return true;
		}
		throw new PastleyException("No se ha eliminado el contacto con el id " + id + ".");
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
