package com.pastley.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastley.models.entity.Buy;
import com.pastley.models.repository.BuyRepository;
import com.pastley.util.PastleyInterface;

@Service
public class BuyService implements PastleyInterface<Long, Buy>{

	@Autowired
	BuyRepository buyRepository;
	
	@Autowired
	BuyDetailService buyDetailService;
	
	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Buy> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buy findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Buy> findByStatuAll(boolean statu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buy save(Buy entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
