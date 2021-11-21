package com.pastley.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pastley.model.entity.Person;
import com.pastley.model.entity.TypeDocument;
import com.pastley.model.repository.TypeDocumentRepository;
import com.pastley.util.PastleyDate;
import com.pastley.util.PastleyInterface;
import com.pastley.util.PastleyValidate;
import com.pastley.util.exception.PastleyException;

/**
 * @project Pastley-User.
 * @author Leyner Jose Ortega Arias.
 * @Github https://github.com/leynerjoseoa.
 * @contributors soleimygomez, serbuitrago, jhonatanbeltran.
 * @version 1.0.0.
 */
@Service
public class TypeDocumentService implements PastleyInterface<Long, TypeDocument> {

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
			throw new PastleyException(HttpStatus.NOT_FOUND, "No existe ningun tipo de documento con el id " + id + ".");
		return typeDocument.orElse(null);
	}

	public TypeDocument findByName(String name) {
		if (!PastleyValidate.isChain(name))
			throw new PastleyException(HttpStatus.NOT_FOUND, "EL tipo de documento no es valido.");
		TypeDocument typeDocument = typeDocumentDAO.findByName(name);
		if (typeDocument == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No existe ningun tipo de documento con el nombre " + name + ".");
		return typeDocument;
	}

	@Override
	public List<TypeDocument> findAll() {
		return typeDocumentDAO.findAll();
	}

	/**
	 * 
	 */
	@Override
	public TypeDocument save(TypeDocument entity) {
		return null;
	}

	public TypeDocument save(TypeDocument entity, byte type) {
		if (entity != null) {
			String message = entity.validate(false);
			String messageType = (type == 1) ? "registrar"
					: ((type == 2) ? "actualizar" : ((type == 3) ? "actualizar estado" : "n/a"));
			if (message == null) {
				TypeDocument typeDocument = (entity.getId() != null && entity.getId() > 0) ? saveToUpdate(entity, type) : saveToSave(entity, type);
				typeDocument = typeDocumentDAO.save(typeDocument);
				if (typeDocument != null) {
					return typeDocument;
				} else {
					throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " el tipo de documento.");
				}
			} else {
				throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " el tipo de documento, " + message + ".");
			}
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha recibido el tipo de documento.");
		}
	}

	private TypeDocument saveToSave(TypeDocument entity, byte type) {
		if (validateName(entity.getName())) {
			PastleyDate date = new PastleyDate();
			entity.uppercase();
			entity.setId(0L);
			entity.setDateRegister(date.currentToDateTime(null));
			entity.setDateUpdate(null);
			entity.setStatu(true);
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"Ya existe un tipo dedocumento con el nombre " + entity.getName() + ".");
		}
		return entity;
	}

	private TypeDocument saveToUpdate(TypeDocument entity, byte type) {
		TypeDocument method = findById(entity.getId());
		if (method != null) {
			boolean isName = (!method.getName().equalsIgnoreCase(entity.getName())) ? validateName(entity.getName())
					: true;
			if (isName) {
				PastleyDate date = new PastleyDate();
				entity.uppercase();
				entity.setDateRegister(method.getDateRegister());
				entity.setDateUpdate(date.currentToDateTime(null));
				entity.setStatu((type == 3) ? !entity.isStatu() : entity.isStatu());
			} else {
				throw new PastleyException(HttpStatus.NOT_FOUND,
						"Ya existe un tipo de documento con el nombre " + entity.getName() + ".");
			}
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha encontrado tipo de documento metodo de pago con el id " + entity.getId() + ".");
		}
		return entity;
	}

	private boolean validateName(String name) {
		TypeDocument typeDocument = null;
		try {
			typeDocument = findByName(name);
		} catch (PastleyException e) {
		}
		return (typeDocument == null) ? true : false;
	}

	
	@Override
	public boolean delete(Long id) {
		findById(id);
		List<Person> list = personService.findByIdTypeDocumentAll(id);
		if(!list.isEmpty())
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha eliminado el tipo de documento con el id  "+id +  ", tiene asociado "+list.size()+" personas.");
		typeDocumentDAO.deleteById(id);
		try {
			if (findById(id) == null) {
				return true;
			}
		} catch (PastleyException e) {
			return true;
		}
		throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha eliminado el tipo de documento con el id " + id + ".");
	}

	@Override
	public List<TypeDocument> findByStatuAll(boolean statu) {
		return new ArrayList<>();
	}

}