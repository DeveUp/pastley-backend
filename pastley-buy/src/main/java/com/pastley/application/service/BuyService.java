package com.pastley.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pastley.infrastructure.config.PastleyValidate;
import com.pastley.application.repository.BuyRepository;
import com.pastley.domain.Buy;
import com.pastley.domain.BuyDetail;
import com.pastley.infrastructure.config.PastleyDate;
import com.pastley.infrastructure.config.PastleyInterface;
import com.pastley.infrastructure.exception.PastleyException;

@Service
public class BuyService implements PastleyInterface<Long, Buy> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BuyService.class);

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
		entity.calculate();
		Buy buy = (entity.getId() != null && entity.getId() > 0) ? saveToUpdate(entity, type)
				: saveToSave(entity, type);
		buy = buyRepository.save(buy);
		if (buy == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " la compra.");
		if(type == 1) {
			saveToDetail(buy, entity.getDetails());
			//buy.setDetails(buyDetailService.findByBuyAll(buy.getId()));
		}
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
	
	private List<BuyDetail> saveToDetail(Buy buy, List<BuyDetail> list) {
		if(!PastleyValidate.isList(list))
			return new ArrayList<>();
		List<BuyDetail> aux = new ArrayList<>();
		list.stream().forEach(d -> {
			d.setId(0L);
			d.setBuy(buy);
			try {
				aux.add(buyDetailService.save(d));
			}catch (PastleyException e) {
				LOGGER.error("[saveToDetail(Buy buy, List<BuyDetail> list)]: "+d.getIdProduct(), e);
			}
		});
		return aux;
	}

	private Buy saveToUpdate(Buy entity, int type) {
		return entity;
	}
}
