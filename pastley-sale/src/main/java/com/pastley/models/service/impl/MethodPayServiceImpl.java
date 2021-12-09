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
import com.pastley.models.entity.MethodPay;
import com.pastley.models.entity.validator.MethodPayValidator;
import com.pastley.models.repository.MethodPayRepository;
import com.pastley.models.service.MethodPayService;
import com.pastley.util.PastleyDate;
import com.pastley.util.PastleyValidate;
import com.pastley.util.exception.PastleyException;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Service
public class MethodPayServiceImpl implements MethodPayService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MethodPayServiceImpl.class);
	
	@Autowired
	MethodPayRepository methodPayRepository;

	@Override
	public MethodPay findById(Long id) {
		if (!PastleyValidate.isLong(id))
			throw new PastleyException("El id del metodo de pago no es valido.");
		Optional<MethodPay> method = methodPayRepository.findById(id);
		if (!method.isPresent())
			throw new PastleyException("No se ha encontrado ningun metodo de pago con el id " + id + ".");
		return method.orElse(null);
	}

	@Override
	public MethodPay findByName(String name) {
		if (!PastleyValidate.isChain(name))
			throw new PastleyException("El nombre del metodo de pago no es valido.");
		MethodPay method = methodPayRepository.findByName(name);
		if (method == null)
			throw new PastleyException("No se ha encontrado ningun metodo de pago con el nombre " + name + ".");
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

	@Override
	public List<MethodPay> findByRangeDateRegister(String start, String end) {
		String arrayDate[] = PastleyValidate.isRangeDateRegisterValidateDate(start, end);
		return methodPayRepository.findByRangeDateRegister(arrayDate[0], arrayDate[1]);
	}

	@Override
	public Long findByStatisticSalePrivate(Long id) {
		if (!PastleyValidate.isLong(id))
			throw new PastleyException("El id del metodo de pago no es valido.");
		Long count = methodPayRepository.countByMethodPaySale(id);
		return count == null ? 0L : count;
	}

	@Override
	public StatisticDTO<MethodPay> findByStatisticSale(Long id) {
		return new StatisticDTO<>(findById(id), findByStatisticSalePrivate(id));
	}

	@Override
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
	public MethodPay save(MethodPay entity, int type) {
		MethodPayValidator.validator(entity);
		String messageType = PastleyValidate.messageToSave(type, false);
		MethodPay method = PastleyValidate.isLong(entity.getId()) ? saveToUpdate(entity, type)
				: saveToSave(entity, type);
		method = methodPayRepository.save(method);
		if (method == null)
			throw new PastleyException("No se ha " + messageType + " el metodo de pago.");
		return method;
	}

	@Override
	public boolean delete(Long id) {
		findById(id);
		Long count = findByStatisticSalePrivate(id);
		if (count >= 0L)
			throw new PastleyException("No se ha eliminado el metodo de pago con el id " + id + ", tiene asociada " + count + " ventas.");
		methodPayRepository.deleteById(id);
		try {
			if (findById(id) == null)
				return true;
		} catch (PastleyException e) {
			LOGGER.error("[delete(Long id)]", e);
			return true;
		}
		throw new PastleyException("No se ha eliminado el metodo de pago con el id " + id + ".");
	}
	
	private MethodPay saveToSave(MethodPay entity, int type) {
		if (!validateName(entity.getName()))
			throw new PastleyException("Ya existe un metodo de pago con el nombre " + entity.getName() + ".");
		PastleyDate date = new PastleyDate();
		uppercase(entity);
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
		if (!testName(entity.getName(), (type == 2 ? method.getName() : entity.getName())))
			throw new PastleyException("Ya existe un metodo de pago con el nombre " + entity.getName() + ".");
		PastleyDate date = new PastleyDate();
		uppercase(entity);
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
	
	private void uppercase(MethodPay methodPay) {
		methodPay.setName(PastleyValidate.uppercase(methodPay.getName()));
	}
}
