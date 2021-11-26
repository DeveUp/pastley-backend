package com.pastley.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastley.models.entity.BuyDetail;
import com.pastley.models.repository.BuyDetailRepository;
import com.pastley.util.PastleyInterface;

@Service
public class BuyDetailService implements PastleyInterface<Long, BuyDetail>{

	@Autowired
	BuyDetailRepository buyDetailRepository;
	
	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<BuyDetail> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuyDetail findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BuyDetail> findByStatuAll(boolean statu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuyDetail save(BuyDetail entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
