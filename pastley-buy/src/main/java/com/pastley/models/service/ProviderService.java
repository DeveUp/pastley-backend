package com.pastley.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastley.models.entity.Provider;
import com.pastley.models.repository.ProviderRepository;
import com.pastley.util.PastleyInterface;

@Service
public class ProviderService implements PastleyInterface<Long, Provider>{
	
	@Autowired
	ProviderRepository providerRepository;

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Provider> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Provider findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Provider> findByStatuAll(boolean statu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Provider save(Provider entity) {
		// TODO Auto-generated method stub
		return null;
	}
}
