package com.pastley.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pastley.model.entity.Role;
import com.pastley.model.entity.User;
import com.pastley.model.repository.RoleRepository;
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
public class RoleService implements PastleyInterface<Long, Role> {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserService userService;

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

	/**
	 * 
	 */
	@Override
	public Role save(Role entity) {
		return null;
	}

	public Role save(Role entity, byte type) {
		if (entity != null) {
			String message = entity.validate(false);
			String messageType = (type == 1) ? "registrar"
					: ((type == 2) ? "actualizar" : ((type == 3) ? "actualizar estado" : "n/a"));
			if (message == null) {
				Role role = (entity.getId() != null && entity.getId() > 0) ? saveToUpdate(entity, type) : saveToSave(entity, type);
				role = roleRepository.save(role);
				if (role != null) {
					return role;
				} else {
					throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " el rol.");
				}
			} else {
				throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " el rol, " + message + ".");
			}
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha recibido el rol.");
		}
	}

	private Role saveToSave(Role entity, byte type) {
		if (validateName(entity.getName())) {
			PastleyDate date = new PastleyDate();
			entity.uppercase();
			entity.setId(0L);
			entity.setDateRegister(date.currentToDateTime(null));
			entity.setDateUpdate(null);
			entity.setStatu(true);
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"Ya existe un rol con el nombre " + entity.getName() + ".");
		}
		return entity;
	}

	private Role saveToUpdate(Role entity, byte type) {
		Role role = findById(entity.getId());
		if (role != null) {
			boolean isName = (!role.getName().equalsIgnoreCase(entity.getName())) ? validateName(entity.getName())
					: true;
			if (isName) {
				PastleyDate date = new PastleyDate();
				entity.uppercase();
				entity.setDateRegister(role.getDateRegister());
				entity.setDateUpdate(date.currentToDateTime(null));
				entity.setStatu((type == 3) ? !entity.isStatu() : entity.isStatu());
			} else {
				throw new PastleyException(HttpStatus.NOT_FOUND,
						"Ya existe un rol con el nombre " + entity.getName() + ".");
			}
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha encontrado rol con el id " + entity.getId() + ".");
		}
		return entity;
	}

	private boolean validateName(String name) {
		Role role = null;
		try {
			role = findByName(name);
		} catch (PastleyException e) {
		}
		return (role == null) ? true : false;
	}
	
	@Override
	public boolean delete(Long id) {
		findById(id);
		List<User> list  = userService.findByIdRole(id);
		if(!list.isEmpty())
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha eliminado el rol con el id  "+id +  ", tiene asociado "+list.size()+" usuarios.");
		roleRepository.deleteById(id);
		try {
			if (findById(id) == null) {
				return true;
			}
		} catch (PastleyException e) {
			return true;
		}
		throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha eliminado el rol con el id " + id + ".");
	}

	@Override
	public List<Role> findByStatuAll(boolean statu) {
		return new ArrayList<>();
	}
}