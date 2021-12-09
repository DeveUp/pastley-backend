package com.pastley.models.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastley.models.entity.Person;
import com.pastley.models.entity.User;
import com.pastley.models.entity.validator.UserValidator;
import com.pastley.models.repository.UserRepository;
import com.pastley.models.service.PersonService;
import com.pastley.models.service.RoleService;
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
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	RoleService roleService;

	@Autowired
	PersonService personService;

	@Autowired
	UserRepository userRepository;

	@Override
	public User findById(Long id) {
		if (!PastleyValidate.isLong(id))
			throw new PastleyException("El id del usuario no es valido.");
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent())
			throw new PastleyException("No existe ningun usuario con el id " + id + ".");
		return user.orElse(null);
	}

	@Override
	public User findByNickName(String nickname) {
		if (!PastleyValidate.isChain(nickname))
			throw new PastleyException("El apodo del usuario no es valido.");
		User user = userRepository.findByNickname(nickname);
		if (user == null)
			throw new PastleyException("No existe ningun usuario con el apodo " + nickname + ".");
		return user;
	}

	@Override
	public User findByDocumentPerson(Long documentPerson) {
		if (!PastleyValidate.isLong(documentPerson))
			throw new PastleyException("El documento de la persona no es valido.");
		User user = userRepository.findByDocumentPerson(documentPerson);
		if (user == null)
			throw new PastleyException("No existe ningun usuario con el documento " + documentPerson + ".");
		return user;
	}

	@Override
	public User findByNicknameAndIdRol(String nickname, Long idRole) {
		if (!PastleyValidate.isChain(nickname))
			throw new PastleyException("El apodo del usuario no es valido.");
		if (!PastleyValidate.isLong(idRole))
			throw new PastleyException("El id del rol no es valido.");
		User user = userRepository.findByNicknameAndIdRol(nickname, idRole);
		if (user == null)
			throw new PastleyException(
					"No existe ningun usuario con el apodo " + nickname + " y id rol " + idRole + ".");
		return user;
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public List<User> findByStatuAll(boolean statu) {
		return userRepository.findByStatu(statu);
	}

	@Override
	public List<User> findByIdRole(Long idRole) {
		if (!PastleyValidate.isLong(idRole))
			throw new PastleyException("El id del rol no es valido.");
		return userRepository.findByIdRole(idRole);
	}

	@Override
	public User saveAndPersonSaveOrUpdate(User entity) {
		UserValidator.validatorStrict(entity);
		User user = new User();
		Person person = personService.saveOrUpdate(entity.getPerson());
		boolean error = false;
		try {
			user = findByNickName(entity.getNickname());
			entity.setId(user.getId());
			entity.setPerson(person);
			error = true;
			user = save(entity, entity.getRole().getId(), 2);
		} catch (PastleyException e) {
			LOGGER.error("[saveAndPersonSaveOrUpdate(User entity)]: User", e);
			if (!error)
				user = save(entity, entity.getRole().getId(), 1);
			else
				throw new PastleyException(e.getMessage());
		}
		return user;
	}

	@Override
	public User save(User entity, Long idRole, int type) {
		UserValidator.validatorPerson(entity);
		String messageType = PastleyValidate.messageToSave(type, false);
		if (type == 1 || type == 2) {
			entity.setRole(roleService.findById(idRole));
			entity.setPerson(personService.findByDocument(entity.getPerson().getDocument()));
			if(!validateNicknameAndRole(entity.getNickname(), idRole))
				throw new PastleyException("Ya existe un usuario con ese apodo y rol.");
		}
		entity = PastleyValidate.isLong(entity.getId()) ? saveToUpdate(entity, idRole, type)
				: saveToSave(entity, idRole, type);
		entity = userRepository.save(entity);
		if (entity == null)
			throw new PastleyException("No se ha " + messageType + " usuario.");
		return entity;
	}

	@Override
	public boolean delete(Long id) {
		User user = findById(id);
		if (user.isStatu())
			throw new PastleyException("No se ha eliminado el usuario, tiene el estado activado.");
		if (user.isSession())
			throw new PastleyException("No se ha eliminado el usuario, tiene la sesión activada.");
		if (PastleyValidate.isLong(user.getPoints()))
			throw new PastleyException("No se ha eliminado el usuario, tiene puntos vigentes.");
		userRepository.deleteById(id);
		try {
			if (findById(id) == null)
				return true;
		} catch (PastleyException e) {
			LOGGER.error("[delete(Long id)]", e);
			return true;
		}
		throw new PastleyException("No se ha eliminado el usuario con el id " + id + ".");
	}

	private User saveToSave(User entity, Long idRole, int type) {
		if (!validateNickname(entity.getNickname()))
			throw new PastleyException("Ya existe una persona con el apodo " + entity.getNickname() + ".");
		PastleyDate date = new PastleyDate();
		entity.setId(0L);
		// entity.setPassword(passwordEncode.encode(entity.getPassword()));
		entity.setSession(false);
		entity.setStatu(true);
		entity.setDateRegister(date.currentToDateTime(null));
		entity.setDateUpdate(null);
		entity.setDateSession(null);
		entity.setDateLastDate(null);
		entity.setPoints(0L);
		return entity;
	}

	private User saveToUpdate(User entity, Long idRole, int type) {
		User user = null;
		if (type == 2) {
			user = findById(entity.getId());
			if (!testNickname(entity.getNickname(), user.getNickname()))
				throw new PastleyException("Ya existe un usuario con ese apodo " + entity.getNickname() + ".");
		}
		PastleyDate date = new PastleyDate();
		// entity.setPassword(type == 2 ? passwordEncode.encode(entity.getPassword()) :
		// user.getPassword());
		entity.setDateRegister(type == 2 ? user.getDateRegister() : entity.getDateRegister());
		entity.setDateUpdate(date.currentToDateTime(null));
		entity.setStatu(type == 3 ? !entity.isStatu() : type == 2 ? user.isStatu() : entity.isStatu());
		return entity;
	}
	
	private boolean validateNicknameAndRole(String nickname, Long idRole) {
		User user = null;
		try {
			user = findByNicknameAndIdRol(nickname, idRole);
		} catch (PastleyException e) {
			LOGGER.error("[validateNicknameAndRole(String nickname, Long idRole)]", e);
		}
		return (user == null) ? true : false;
	}

	private boolean validateNickname(String nickname) {
		User user = null;
		try {
			user = findByNickName(nickname);
		} catch (PastleyException e) {
			LOGGER.error("[validateNickname(String nickname)]", e);
		}
		return (user == null) ? true : false;
	}

	private boolean testNickname(String nicknameA, String nicknameB) {
		return !nicknameA.equalsIgnoreCase(nicknameB) ? validateNickname(nicknameA) : true;
	}
}
