package com.pastley.models.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastley.models.dto.StatisticDTO;
import com.pastley.models.entity.TypePQR;
import com.pastley.models.entity.validator.TypePQRValidator;
import com.pastley.models.repository.TypePQRRepository;
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
public class TypePQRServiceImpl implements TypePQRService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TypePQRService.class);

	@Autowired
	TypePQRRepository typePQRRepository;

	@Override
	public TypePQR findById(Long id) {
		if (!PastleyValidate.isLong(id))
			throw new PastleyException("El id del tipo de pqr no es valido.");
		Optional<TypePQR> type = typePQRRepository.findById(id);
		if (!type.isPresent())
			throw new PastleyException("No se ha encontrado ningun tipo de pqr con el id " + id + ".");
		return type.orElse(null);
	}

	public TypePQR findByName(String name) {
		if (!PastleyValidate.isChain(name))
			throw new PastleyException("El nombre del tipo de pqr no es valido.");
		TypePQR type = typePQRRepository.findByName(name);
		if (type == null)
			throw new PastleyException("No se ha encontrado ningun tipo de pqr con el nombre " + name + ".");
		return type;
	}

	@Override
	public List<TypePQR> findAll() {
		return typePQRRepository.findAll();
	}

	@Override
	public List<TypePQR> findByStatuAll(boolean statu) {
		return typePQRRepository.findByStatu(statu);
	}

	public List<TypePQR> findByRangeDateRegister(String start, String end) {
		String arrayDate[] = PastleyValidate.isRangeDateRegisterValidateDate(start, end);
		return typePQRRepository.findByRangeDateRegister(arrayDate[0], arrayDate[1]);
	}

	public Long findByStatisticTypePrivate(Long id) {
		if (!PastleyValidate.isLong(id))
			throw new PastleyException("El id del tipo de pqr no es valido.");
		Long count = typePQRRepository.countByTypePQR(id);
		return count == null ? 0L : count;
	}

	public StatisticDTO<TypePQR> findByStatisticType(Long id) {
		return new StatisticDTO<>(findById(id), findByStatisticTypePrivate(id));
	}

	public List<StatisticDTO<TypePQR>> findByStatisticTypeAll() {
		try {
			List<TypePQR> type = typePQRRepository.findByStatu(true);
			return type.stream().map(tp -> new StatisticDTO<>(tp, findByStatisticTypePrivate(tp.getId())))
					.collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("[findByStatisticTypeAll()]", e);
			return new ArrayList<>();
		}
	}

	public TypePQR save(TypePQR entity, int type) {
		TypePQRValidator.validator(entity);
		String messageType = PastleyValidate.messageToSave(type, false);
		TypePQR typePqr = (PastleyValidate.isLong(entity.getId())) ? saveToUpdate(entity, type)
				: saveToSave(entity, type);
		typePqr = typePQRRepository.save(typePqr);
		if (typePqr == null)
			throw new PastleyException("No se ha " + messageType + " el tipo pqr.");
		return typePqr;
	}

	@Override
	public boolean delete(Long id) {
		findById(id);
		Long count = findByStatisticTypePrivate(id);
		if (PastleyValidate.isLong(count))
			throw new PastleyException("No se ha eliminado el tipo de pqr con el id " + id + ", tiene asociado " + count + " contactos.");
		typePQRRepository.deleteById(id);
		try {
			if (findById(id) == null)
				return true;
		} catch (PastleyException e) {
			LOGGER.error("[delete(Long id)]", e);
			return true;
		}
		throw new PastleyException("No se ha eliminado el tipo de pqr con el id " + id + ".");
	}

	private TypePQR saveToSave(TypePQR entity, int type) {
		if (!validateName(entity.getName()))
			throw new PastleyException("Ya existe un tipo de pqr con el nombre " + entity.getName() + ".");
		PastleyDate date = new PastleyDate();
		uppercase(entity);
		entity.setId(0L);
		entity.setDateRegister(date.currentToDateTime(null));
		entity.setDateUpdate(null);
		entity.setStatu(true);
		return entity;
	}

	private TypePQR saveToUpdate(TypePQR entity, int type) {
		TypePQR typePqr = null;
		if (type != 3)
			typePqr = findById(entity.getId());
		if (!testName(entity.getName(), (type == 3) ? entity.getName() : typePqr.getName()))
			throw new PastleyException("Ya existe un tipo de pqr con el nombre " + entity.getName() + ".");
		PastleyDate date = new PastleyDate();
		uppercase(entity);
		entity.setDateRegister((type == 3) ? entity.getDateRegister() : typePqr.getDateRegister());
		entity.setDateUpdate(date.currentToDateTime(null));
		entity.setStatu((type == 3) ? !entity.isStatu() : entity.isStatu());
		return entity;
	}

	private boolean validateName(String name) {
		TypePQR type = null;
		try {
			type = findByName(name);
		} catch (PastleyException e) {
			LOGGER.error("[validateName(String name)]", e);
			type = null;
		}
		return (type == null) ? true : false;
	}

	private boolean testName(String nameA, String nameB) {
		return (!nameA.equalsIgnoreCase(nameB)) ? validateName(nameA) : true;
	}
	
	private void uppercase(TypePQR typePQR) {
		
	}
}
