package com.pastley.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pastley.infrastructure.config.PastleyValidate;
import com.pastley.application.repository.BuyDetailRepository;
import com.pastley.domain.BuyDetail;
import com.pastley.infrastructure.config.PastleyInterface;
import com.pastley.infrastructure.exception.PastleyException;

@Service
public class BuyDetailService implements PastleyInterface<Long, BuyDetail>{

	@Autowired
	BuyDetailRepository buyDetailRepository;
	
	@Autowired
	BuyService buyService;
	
	@Override
	public BuyDetail findById(Long id) {
		if (!PastleyValidate.isLong(id))
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del detalle de compra no es valido.");
		Optional<BuyDetail> buyDetail = buyDetailRepository.findById(id);
		if (!buyDetail.isPresent())
			throw new PastleyException(HttpStatus.NOT_FOUND, "No existe ningun detalle de compra con el id " + id + ".");
		return buyDetail.orElse(null);
	}
	
	@Override
	public List<BuyDetail> findAll() {
		return buyDetailRepository.findAll();
	}
	
	@Override
	public List<BuyDetail> findByStatuAll(boolean statu) {
		return new ArrayList<>();
	}
	
	
	public List<BuyDetail> findByBuyAll(Long idBuy){
		if(!PastleyValidate.isLong(idBuy))
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id de la compra no es valido.");
		return buyDetailRepository.findByBuy(idBuy);
	}

	@Override
	public BuyDetail save(BuyDetail entity) {
		if(entity == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha recibido la compra.");
		String message = entity.validate(true);
		if (message != null)
			throw new PastleyException(HttpStatus.NOT_FOUND, message);
		String messageType = PastleyValidate.messageToSave(!PastleyValidate.isLong(entity.getId()) ? 1 : 2, false);
		entity.setBuy(buyService.findById(entity.getBuy().getId()));
		BuyDetail buyDetail = (PastleyValidate.isLong(entity.getId())) ? saveToUpdate(entity)
				: saveToSave(entity);
		buyDetail = buyDetailRepository.save(buyDetail);
		if (buyDetail == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " la compra.");
		buyDetail.calculate();
		return buyDetail;
	}
	
	@Override
	public boolean delete(Long id) {
		return false;
	}
	
	private BuyDetail saveToSave(BuyDetail entity) {
		entity.setId(0L);
		entity.calculate();
		entity.addDescription();
		return entity;
	}
	
	private BuyDetail saveToUpdate(BuyDetail entity) {
		return entity;
	}
}
