package com.pastley.models.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastley.models.entity.Role;
import com.pastley.models.entity.User;
import com.pastley.models.entity.validator.RoleValidator;
import com.pastley.models.repository.RoleRepository;
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
public class RoleServiceImpl implements RoleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserService userService;

	@Override
	public Role findById(Long id) {
		if (!PastleyValidate.isLong(id))
			throw new PastleyException("El id del rol no es valido.");
		Optional<Role> role = roleRepository.findById(id);
		if (!role.isPresent())
			throw new PastleyException("No existe ningun rol con el id " + id + ".");
		return role.orElse(null);
	}

	@Override
	public Role findByName(String name) {
		if (!PastleyValidate.isChain(name))
			throw new PastleyException("El nombre del rol no es valido.");
		Role role = roleRepository.findByName(name);
		if (role == null)
			throw new PastleyException("No existe ningun rol con el nombre " + name + ".");
		return role;
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Role save(Role entity, int type) {
		RoleValidator.validator(entity);
		String messageType = PastleyValidate.messageToSave(type, false);
		Role role = PastleyValidate.isLong(entity.getId()) ? saveToUpdate(entity, type) : saveToSave(entity, type);
		role = roleRepository.save(role);
		if (role == null)
			throw new PastleyException("No se ha " + messageType + " el rol.");
		return role;
	}

	@Override
	public boolean delete(Long id) {
		findById(id);
		List<User> list = userService.findByIdRole(id);
		if (!list.isEmpty())
			throw new PastleyException(
					"No se ha eliminado el rol con el id  " + id + ", tiene asociado " + list.size() + " usuarios.");
		roleRepository.deleteById(id);
		try {
			if (findById(id) == null)
				return true;
		} catch (PastleyException e) {
			LOGGER.error("[delete(Long id)]", e);
			return true;
		}
		throw new PastleyException("No se ha eliminado el rol con el id " + id + ".");
	}

	private Role saveToSave(Role entity, int type) {
		if (!validateName(entity.getName()))
			throw new PastleyException("Ya existe un rol con el nombre " + entity.getName() + ".");
		PastleyDate date = new PastleyDate();
		uppercase(entity);
		entity.setId(0L);
		entity.setDateRegister(date.currentToDateTime(null));
		entity.setDateUpdate(null);
		entity.setStatu(true);
		return entity;
	}

	private Role saveToUpdate(Role entity, int type) {
		Role role = null;
		if (type == 2) {
			role = findById(entity.getId());
			if (!testName(entity.getName(), role.getName()))
				throw new PastleyException("Ya existe un rol con el nombre " + entity.getName() + ".");
		}
		PastleyDate date = new PastleyDate();
		uppercase(entity);
		entity.setDateRegister((type == 2) ? role.getDateRegister() : entity.getDateRegister());
		entity.setDateUpdate(date.currentToDateTime(null));
		entity.setStatu((type == 3) ? !entity.isStatu() : type == 2 ? role.isStatu() : entity.isStatu());
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

	private void uppercase(Role role) {
		role.setName(PastleyValidate.uppercase(role.getName()));
	}
}
