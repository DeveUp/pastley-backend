package com.pastley.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pastley.model.entity.Person;
import com.pastley.model.entity.User;
import com.pastley.model.repository.UserRepository;
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
public class UserService implements PastleyInterface<Long, User> {

	@Autowired
	private UserRepository userDAO;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PersonService personService;

	/**
	 * 
	 */
	@Override
	public User findById(Long id) {
		if (id <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del usuario no es valido.");
		Optional<User> user = userDAO.findById(id);
		if (!user.isPresent())
			throw new PastleyException(HttpStatus.NOT_FOUND, "No existe ningun usuario con el id " + id + ".");
		return user.orElse(null);
	}

	public User findByNickName(String nickname) {
		if (!PastleyValidate.isChain(nickname))
			throw new PastleyException(HttpStatus.NOT_FOUND, "El apodo del usuario no es valido.");
		User user = userDAO.findByNickname(nickname);
		if (user == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No existe ningun usuario con el apodo " + nickname + ".");
		return user;
	}

	public User findByIdAndIdRol(Long id, Long idRole) {
		if (id <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del usuario no es valido.");
		if (idRole <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del rol no es valido.");
		User user = userDAO.findByIdAndIdRol(id, idRole);
		if (user == null)
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No existe ningun usuario con el id " + id + " y el rol con id " + idRole + ".");
		return user;
	}

	/**
	 * 
	 */
	@Override
	public List<User> findAll() {
		return userDAO.findAll();
	}

	/**
	 * 
	 * @param idRole
	 * @return
	 */
	public List<User> findByIdRole(Long idRole) {
		if (idRole <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del rol no es valido.");
		roleService.findById(idRole);
		return userDAO.findByIdRole(idRole);
	}

	/**
	 * 
	 * @param idPerson
	 * @return
	 */
	public User findByDocumentPerson(Long documentPerson) {
		if (documentPerson <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El documento de la persona no es valido.");
		User user = userDAO.findByDocumentPerson(documentPerson);
		if (user == null)
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No existe ningun usuario con el documento " + documentPerson + ".");
		return user;
	}

	/**
	 * 
	 */
	@Override
	public User save(User entity) {
		return null;
	}

	public User save(User entity, Long idRole, byte type) {
		if (entity != null) {
			String message = entity.validate(false);
			if (message == null) {
				String message_type = (type == 1) ? "registrado" : (type == 2) ? "actualizado" : "actualizando estado";
				if (type != 3) {
					Person person = saveFindPerson(entity.getPerson().getDocument());
					person = saveToPerson((person == null) ? entity.getPerson() : person,
							(person == null) ? (byte) 1 : (byte) 2);
				}
				entity = (entity.getId() > 0) ? saveToUpdate(entity, idRole, type) : saveToSave(entity, idRole, type);
				entity = userDAO.save(entity);
				if (entity != null && type == 1)
					entity = userDAO.createUserRole(entity.getId(), idRole);
				if (entity == null)
					throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + message_type + " usuario.");
				return entity;
			} else {
				throw new PastleyException(HttpStatus.NOT_FOUND, message);
			}
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha recibido el usuario.");
		}
	}

	private User saveToSave(User entity, Long idRole, byte type) {
		saveFindByNickname(entity.getNickname());
		PastleyDate date = new PastleyDate();
		entity.date(date.currentToDateTime(null), null);
		entity.is(true, false);
		entity.setPoints(0L);
		return entity;
	}

	/**
	 * 
	 * @param entity
	 * @param idRole
	 * @param type
	 * @return
	 */
	private User saveToUpdate(User entity, Long idRole, byte type) {
		User user = findById(entity.getId());
		if (!entity.getNickname().equalsIgnoreCase(user.getNickname()))
			saveFindByNickname(entity.getNickname());
		PastleyDate date = new PastleyDate();
		entity.date(user.getDateRegister(), date.currentToDateTime(null));
		entity.is((type == 3) ? !user.isStatu() : user.isStatu(), user.isStatu());
		return entity;
	}

	private User saveFindByNickname(String nickname) {
		User user = null;
		try {
			user = findByNickName(nickname);
		} catch (Exception e) {
			user = null;
		} finally {
			if (user != null)
				throw new PastleyException(HttpStatus.NOT_FOUND, "Ya existe un usuario con ese apodo.");
		}
		return null;
	}

	/**
	 * 
	 * @param documentPerson
	 * @return
	 */
	private Person saveFindPerson(long documentPerson) {
		try {
			return personService.findByDocument(documentPerson);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * @param person
	 * @param type
	 * @return
	 */
	private Person saveToPerson(Person person, byte type) {
		return personService.save(person, type);
	}

	@Override
	public boolean delete(Long id) {
		return false;
	}

	@Override
	public List<User> findByStatuAll(boolean statu) {
		return new ArrayList<>();
	}

}