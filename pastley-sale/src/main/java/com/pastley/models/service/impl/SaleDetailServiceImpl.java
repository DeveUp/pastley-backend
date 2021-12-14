package com.pastley.models.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastley.models.entity.SaleDetail;
import com.pastley.models.entity.validator.SaleDetailtValidator;
import com.pastley.models.repository.SaleDetailRepository;
import com.pastley.models.service.CartService;
import com.pastley.models.service.SaleDetailService;
import com.pastley.models.service.SaleService;
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
public class SaleDetailServiceImpl implements SaleDetailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SaleDetailService.class);

	@Autowired
	SaleDetailRepository saleDetailRepository;
	
	@Autowired
	SaleService saleService;
	
	@Autowired
	CartService cartService;

	@Override
	public SaleDetail findById(Long id) {
		if (!PastleyValidate.isLong(id))
			throw new PastleyException("El id del detalle de venta no es valido.");
		Optional<SaleDetail> saleDetail = saleDetailRepository.findById(id);
		if (!saleDetail.isPresent())
			throw new PastleyException("No se ha encontrado ningun detalle de venta con el id " + id + ".");
		return saleDetail.orElse(null);
	}

	@Override
	public List<SaleDetail> findAll() {
		return saleDetailRepository.findAll();
	}

	@Override
	public List<SaleDetail> findBySale(Long idSale) {
		if (!PastleyValidate.isLong(idSale))
			throw new PastleyException("El id de la venta no es valida.");
		return saleDetailRepository.findByIdSale(idSale);
	}

	@Override
	public SaleDetail save(SaleDetail entity) {
		SaleDetailtValidator.validator(entity);
		String messageType = PastleyValidate.messageToSave(!PastleyValidate.isLong(entity.getId()) ? 1 : 2, false);
		saleService.findById(entity.getIdSale());
		SaleDetail saleDetail = PastleyValidate.isLong(entity.getId()) ? saveToUpdate(entity) : saveToSave(entity);
		saleDetail = saleDetailRepository.save(saleDetail);
		if (saleDetail == null)
			throw new PastleyException("No se ha " + messageType + " el detalle de venta.");
		return saleDetail;
	}

	@Override
	public boolean delete(Long id) {
		findById(id);
		saleDetailRepository.deleteById(id);
		try {
			if (findById(id) == null)
				return true;
		} catch (PastleyException e) {
			LOGGER.error("[delete(Long id)]", e);
			return true;
		}
		throw new PastleyException("No se ha eliminado el detalle de venta con el id " + id + ".");
	}

	private SaleDetail saveToSave(SaleDetail saleDetail) {
		saleDetail.setId(0L);
		cartService.save(saleDetail.getCart(), 3);
		return saleDetail;
	}

	private SaleDetail saveToUpdate(SaleDetail saleDetail) {
		return saleDetail;
	}
}
