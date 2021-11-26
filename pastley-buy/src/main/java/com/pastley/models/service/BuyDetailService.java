package com.pastley.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pastley.models.entity.BuyDetail;
import com.pastley.models.repository.BuyDetailRepository;
import com.pastley.util.PastleyInterface;
import com.pastley.util.exception.PastleyException;

@Service
public class BuyDetailService implements PastleyInterface<Long, BuyDetail>{

	@Autowired
	BuyDetailRepository buyDetailRepository;
	
	@Override
	public BuyDetail findById(Long id) {
		return null;
	}
	
	@Override
	public List<BuyDetail> findByStatuAll(boolean statu) {
		return null;
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
		BuyDetail buyDetail = null;
		return buyDetail;
	}
	
	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
