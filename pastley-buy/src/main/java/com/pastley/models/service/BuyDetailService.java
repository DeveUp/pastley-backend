package com.pastley.models.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pastley.models.entity.BuyDetail;
import com.pastley.models.repository.BuyDetailRepository;
import com.pastley.util.PastleyInterface;
import com.pastley.util.PastleyValidate;
import com.pastley.util.exception.PastleyException;

@Service
public class BuyDetailService implements PastleyInterface<Long, BuyDetail>{

	@Autowired
	BuyDetailRepository buyDetailRepository;
	
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
	public List<BuyDetail> findByStatuAll(boolean statu) {
		return new ArrayList<>();
	}
	
	@Override
	public List<BuyDetail> findAll() {
		return buyDetailRepository.findAll();
	}
	

	@Override
	public BuyDetail save(BuyDetail entity) {
		if(entity == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha recibido la compra.");
		String message = entity.validate();
		if (message != null)
			throw new PastleyException(HttpStatus.NOT_FOUND, message);
		String messageType = PastleyValidate.messageToSave(entity.getId() <= 0 ? 1 : 2, false);
		BuyDetail buyDetail = (entity.getId() != null && entity.getId() > 0) ? saveToUpdate(entity)
				: saveToSave(entity);
		buyDetail = buyDetailRepository.save(buyDetail);
		if (buyDetail == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " la compra.");
		return buyDetail;
	}
	
	@Override
	public boolean delete(Long id) {
		return false;
	}
	
	private BuyDetail saveToSave(BuyDetail entity) {
		return entity;
	}
	
	private BuyDetail saveToUpdate(BuyDetail entity) {
		return entity;
	}
}
