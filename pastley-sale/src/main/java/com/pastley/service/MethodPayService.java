package com.pastley.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastley.repository.MethodPayRepository;
import com.pastley.entity.MethodPay;
import com.pastley.model.StatisticModel;
import com.pastley.util.PastleyInterface;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors soleimygomez, leynerjoseoa, jhonatanbeltran.
 * @version 1.0.0.
 */
@Service
public class MethodPayService implements PastleyInterface<Long, MethodPay> {

	@Autowired
	private MethodPayRepository methodPayRepository;

	///////////////////////////////////////////////////////
	// Method - Find
	///////////////////////////////////////////////////////
	@Override
	public MethodPay findById(Long id) {
		try {
			return methodPayRepository.findById(id).orElse(null);
		} catch (Exception e) {
			return null;
		}
	}

	public MethodPay findByName(String name) {
		try {
			return methodPayRepository.findByName(name);
		} catch (Exception e) {
			return null;
		}
	}

	///////////////////////////////////////////////////////
	// Method - Find - List
	///////////////////////////////////////////////////////
	@Override
	public List<MethodPay> findAll() {
		try {
			return methodPayRepository.findAll();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	@Override
	public List<MethodPay> findByStatuAll(boolean statu) {
		try {
			return methodPayRepository.findByStatu(statu);
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public List<MethodPay> findByRangeDateRegister(String start, String end) {
		try {
			return methodPayRepository.findByRangeDateRegister(start, end);
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	///////////////////////////////////////////////////////
	// Method - Find - Statistic
	///////////////////////////////////////////////////////
	public Long findByStatisticSale(Long id){
		try {
			Long count = methodPayRepository.countByMethodPaySale(id);
			return count == null ? 0L : count;
		} catch (Exception e) {
			return 0L;
		}
	}
	///////////////////////////////////////////////////////
	// Method - Find - Statistic - List
	///////////////////////////////////////////////////////
	public List<StatisticModel<MethodPay>> findByStatisticSaleAll() {
		try {
			List<MethodPay> methods = methodPayRepository.findByStatu(true);
			List<StatisticModel<MethodPay>> list = new ArrayList<>();
			for (MethodPay mp : methods) {
				list.add(new StatisticModel<MethodPay>(mp, findByStatisticSale(mp.getId())));
			}
			return list;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	///////////////////////////////////////////////////////
	// Method - Save and Update
	///////////////////////////////////////////////////////
	@Override
	public MethodPay save(MethodPay entity) {
		try {
			return methodPayRepository.save(entity);
		} catch (Exception e) {
			return null;
		}
	}

	///////////////////////////////////////////////////////
	// Method - Delete
	///////////////////////////////////////////////////////
	@Override
	public boolean delete(Long id) {
		try {
			methodPayRepository.deleteById(id);
			return findById(id) == null;
		} catch (Exception e) {
			return false;
		}
	}
}
