package com.pastley.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pastley.application.repository.RoleRepository;
import com.pastley.domain.Role;
import com.pastley.domain.User;
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
public class RoleService implements PastleyInterface<Long, Role> {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserService userService;

	@Override
	public Role findById(Long id) {
		if (id <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del rol no es valido.");
		Optional<Role> role = roleRepository.findById(id);
		if (!role.isPresent())
			throw new PastleyException(HttpStatus.NOT_FOUND, "No existe ningun rol con el id " + id + ".");
		return role.orElse(null);
	}

	public Role findByName(String name) {
		if (!PastleyValidate.isChain(name))
			throw new PastleyException(HttpStatus.NOT_FOUND, "EL nombre del rol  no es valido.");
		Role role = roleRepository.findByName(name);
		if (role == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No existe ningun rol con el nombre " + name + ".");
		return role;
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public List<Role> findByStatuAll(boolean statu) {
		return new ArrayList<>();
	}

	@Override
	public Role save(Role entity) {
		return null;
	}

	public Role save(Role entity, int type) {
		if (entity == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha recibido el rol.");
		String message = entity.validate();
		String messageType = PastleyValidate.messageToSave(type, false);
		if (message != null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " el rol, " + message + ".");
		Role role = (entity.getId() != null && entity.getId() > 0) ? saveToUpdate(entity, type)
				: saveToSave(entity, type);
		role = roleRepository.save(role);
		if (role == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " el rol.");
		return role;
	}

	@Override
	public boolean delete(Long id) {
		findById(id);
		List<User> list = userService.findByIdRole(id);
		if (!list.isEmpty())
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha eliminado el rol con el id  " + id + ", tiene asociado " + list.size() + " usuarios.");
		roleRepository.deleteById(id);
		try {
			if (findById(id) == null) {
				return true;
			}
		} catch (PastleyException e) {
			LOGGER.error("[delete(Long id)]", e);
			return true;
		}
		throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha eliminado el rol con el id " + id + ".");
	}

	private Role saveToSave(Role entity, int type) {
		if (!validateName(entity.getName()))
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"Ya existe un rol con el nombre " + entity.getName() + ".");
		PastleyDate date = new PastleyDate();
		entity.uppercase();
		entity.setId(0L);
		entity.setDateRegister(date.currentToDateTime(null));
		entity.setDateUpdate(null);
		entity.setStatu(true);
		return entity;
	}

	private Role saveToUpdate(Role entity, int type) {
		Role role = null;
		if (type != 3)
			role = findById(entity.getId());
		if (!testName(entity.getName(), (type == 3 ? entity.getName() : role.getName())))
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"Ya existe un rol con el nombre " + entity.getName() + ".");
		PastleyDate date = new PastleyDate();
		entity.uppercase();
		entity.setDateRegister((type == 3) ? entity.getDateRegister() : role.getDateRegister());
		entity.setDateUpdate(date.currentToDateTime(null));
		entity.setStatu((type == 3) ? !entity.isStatu() : entity.isStatu());
		return entity;
	}

	private boolean validateName(String name) {
		Role role = null;
		try {
			role = findByName(name);
		} catch (PastleyException e) {
			LOGGER.error("[validateName(String name)]", e);
		}
		return (role == null) ? true : false;
	}

	private boolean testName(String nameA, String nameB) {
		return (!nameA.equalsIgnoreCase(nameB)) ? validateName(nameA) : true;
	}
}