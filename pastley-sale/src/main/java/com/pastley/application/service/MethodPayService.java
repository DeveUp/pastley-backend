package com.pastley.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pastley.application.repository.MethodPayRepository;
import com.pastley.domain.MethodPay;
import com.pastley.infrastructure.config.PastleyDate;
import com.pastley.infrastructure.config.PastleyInterface;
import com.pastley.infrastructure.config.PastleyValidate;
import com.pastley.infrastructure.exception.PastleyException;
import com.pastley.infrastructure.dto.StatisticDTO;


/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Service
public class MethodPayService implements PastleyInterface<Long, MethodPay> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodPayService.class);

	@Autowired
	MethodPayRepository methodPayRepository;

	@Override
	public MethodPay findById(Long id) {
		if (id == null || id <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del metodo de pago no es valido.");
		Optional<MethodPay> method = methodPayRepository.findById(id);
		if (!method.isPresent())
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha encontrado ningun metodo de pago con el id " + id + ".");
		return method.orElse(null);

	}

	public MethodPay findByName(String name) {
		if (!PastleyValidate.isChain(name))
			throw new PastleyException(HttpStatus.NOT_FOUND, "El nombre del metodo de pago no es valido.");
		MethodPay method = methodPayRepository.findByName(name);
		if (method == null)
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha encontrado ningun metodo de pago con el nombre " + name + ".");
		return method;
	}

	@Override
	public List<MethodPay> findAll() {
		return methodPayRepository.findAll();
	}

	@Override
	public List<MethodPay> findByStatuAll(boolean statu) {
		return methodPayRepository.findByStatu(statu);
	}

	public List<MethodPay> findByRangeDateRegister(String start, String end) {
		String arrayDate[] = PastleyValidate.isRangeDateRegisterValidateDate(start, end);
		return methodPayRepository.findByRangeDateRegister(arrayDate[0], arrayDate[1]);
	}

	private Long findByStatisticSalePrivate(Long id) {
		if (id <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del metodo de pago no es valido.");
		Long count = methodPayRepository.countByMethodPaySale(id);
		return count == null ? 0L : count;
	}

	public StatisticDTO<MethodPay> findByStatisticSale(Long id) {
		return new StatisticDTO<>(findById(id), findByStatisticSalePrivate(id));
	}

	public List<StatisticDTO<MethodPay>> findByStatisticSaleAll() {
		try {
			List<MethodPay> methods = methodPayRepository.findByStatu(true);
			return methods.stream().map(m -> new StatisticDTO<>(m, findByStatisticSalePrivate(m.getId())))
					.collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("[findByStatisticSaleAll()]", e);
			return new ArrayList<>();
		}
	}

	@Override
	public MethodPay save(MethodPay entity) {
		return null;
	}

	public MethodPay save(MethodPay entity, int type) {
		if (entity == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha recibido el metodo de pago.");
		String message = entity.validate();
		String messageType = PastleyValidate.messageToSave(type, false);
		if (message != null)
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha " + messageType + " el metodo de pago, " + message + ".");
		MethodPay method = (entity.getId() != null && entity.getId() > 0) ? saveToUpdate(entity, type)
				: saveToSave(entity, type);
		method = methodPayRepository.save(method);
		if (method == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " el metodo de pago.");
		return method;
	}

	@Override
	public boolean delete(Long id) {
		findById(id);
		Long count = findByStatisticSalePrivate(id);
		if (count >= 0L)
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha eliminado el metodo de pago con el id " + id + ", tiene asociada " + count + " ventas.");
		methodPayRepository.deleteById(id);
		try {
			if (findById(id) == null) {
				return true;
			}
		} catch (PastleyException e) {
			LOGGER.error("[delete(Long id)]", e);
			return true;
		}
		throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha eliminado el metodo de pago con el id " + id + ".");
	}

	private MethodPay saveToSave(MethodPay entity, int type) {
		if (!validateName(entity.getName()))
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"Ya existe un metodo de pago con el nombre " + entity.getName() + ".");
		PastleyDate date = new PastleyDate();
		entity.uppercase();
		entity.setId(0L);
		entity.setDateRegister(date.currentToDateTime(null));
		entity.setDateUpdate(null);
		entity.setStatu(true);
		return entity;
	}

	private MethodPay saveToUpdate(MethodPay entity, int type) {
		MethodPay method = null;
		if (type != 3)
			method = findById(entity.getId());
		if (!testName(entity.getName(), (type == 3 ? entity.getName() : method.getName())))
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"Ya existe un metodo de pago con el nombre " + entity.getName() + ".");
		PastleyDate date = new PastleyDate();
		entity.uppercase();
		entity.setDateRegister((type == 3) ? entity.getDateRegister() : method.getDateRegister());
		entity.setDateUpdate(date.currentToDateTime(null));
		entity.setStatu((type == 3) ? !entity.isStatu() : entity.isStatu());
		return entity;
	}

	private boolean validateName(String name) {
		MethodPay method = null;
		try {
			method = findByName(name);
		} catch (PastleyException e) {
			LOGGER.error("[validateName(String name)]", e);
		}
		return (method == null) ? true : false;
	}

	private boolean testName(String nameA, String nameB) {
		return (!nameA.equalsIgnoreCase(nameB)) ? validateName(nameA) : true;
	}
}
