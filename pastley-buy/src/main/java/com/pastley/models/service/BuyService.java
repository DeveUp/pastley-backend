package com.pastley.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pastley.models.entity.Buy;
import com.pastley.models.repository.BuyRepository;
import com.pastley.util.PastleyDate;
import com.pastley.util.PastleyInterface;
import com.pastley.util.PastleyValidate;
import com.pastley.util.exception.PastleyException;

@Service
public class BuyService implements PastleyInterface<Long, Buy> {

	@Autowired
	BuyRepository buyRepository;

	@Autowired
	BuyDetailService buyDetailService;

	@Autowired
	ProviderService providerService;

	@Override
	public Buy findById(Long id) {
		if (!PastleyValidate.isLong(id))
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id de la compra no es valido.");
		Optional<Buy> buy = buyRepository.findById(id);
		if (!buy.isPresent())
			throw new PastleyException(HttpStatus.NOT_FOUND, "No existe ninguna compra con el id " + id + ".");
		return buy.orElse(null);
	}

	@Override
	public List<Buy> findAll() {
		return buyRepository.findAll();
	}

	@Override
	public List<Buy> findByStatuAll(boolean statu) {
		return buyRepository.findByStatu(statu);
	}

	public List<Buy> findByProviderAll(Long idProvider) {
		if (!PastleyValidate.isLong(idProvider))
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del proveedor no es valido.");
		return buyRepository.findByProvider(idProvider);
	}

	public List<Buy> findByRangeDateRegister(String start, String end) {
		String arrayDate[] = PastleyValidate.isRangeDateRegisterValidateDate(start, end);
		return buyRepository.findByRangeDateRegister(arrayDate[0], arrayDate[1]);
	}

	@Override
	public Buy save(Buy entity) {
		return null;
	}

	public Buy save(Buy entity, int type) {
		if (entity == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha recibido la compra.");
		String message = entity.validate();
		if (message != null)
			throw new PastleyException(HttpStatus.NOT_FOUND, message);
		String messageType = PastleyValidate.messageToSave(type, false);
		if (type == 1 || type == 2)
			entity.setProvider(providerService.findById(entity.getProvider().getId()));
		Buy buy = (entity.getId() != null && entity.getId() > 0) ? saveToUpdate(entity, type)
				: saveToSave(entity, type);
		buy = buyRepository.save(buy);
		if (buy == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " la compra.");
		return buy;
	}

	@Override
	public boolean delete(Long id) {
		return false;
	}

	private Buy saveToSave(Buy entity, int type) {
		if (!PastleyValidate.isList(entity.getDetails()))
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha registrado la compra, no se ha recibido ningun detalle.");
		PastleyDate date = new PastleyDate();
		entity.setId(0L);
		entity.setDateRegister(date.currentToDateTime(null));
		entity.setDateUpdate(null);
		entity.setStatu(true);
		return entity;
	}

	private Buy saveToUpdate(Buy entity, int type) {
		return entity;
	}
}
