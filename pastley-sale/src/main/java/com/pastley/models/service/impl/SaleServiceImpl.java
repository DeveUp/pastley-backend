package com.pastley.models.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastley.models.dto.ProductDTO;
import com.pastley.models.entity.Sale;
import com.pastley.models.entity.SaleDetail;
import com.pastley.models.feign.ProductFeignClient;
import com.pastley.models.repository.SaleRepository;
import com.pastley.models.service.SaleDetailService;
import com.pastley.models.service.SaleService;
import com.pastley.util.PastleyDate;
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
public class SaleServiceImpl implements SaleService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SaleDetailServiceImpl.class);

	@Autowired
	SaleRepository saleRepository;
	
	@Autowired
	SaleDetailService saleDetailService;
	
	@Autowired
	ProductFeignClient productFeignClient;
	
	@Override
	public Sale findById(Long id) {
		if (!PastleyValidate.isLong(id))
			throw new PastleyException("El id de la venta no es valido.");
		Optional<Sale> sale = saleRepository.findById(id);
		if (!sale.isPresent())
			throw new PastleyException("No se ha encontrado ninguna venta con el id " + id + ".");
		return sale.orElse(null);
	}

	@Override
	public List<Sale> findAll() {
		return saleRepository.findAll();
	}

	@Override
	public List<Sale> findByIdCustomerAll(Long idCustoner) {
		if (!PastleyValidate.isLong(idCustoner))
			throw new PastleyException("El id del cliente no es valido.");
		return saleRepository.findByIdCoustomer(idCustoner);
	}

	@Override
	public List<Sale> findByIdMethodPayAll(Long idMethodPay) {
		if (!PastleyValidate.isLong(idMethodPay))
			throw new PastleyException("El id del metodo de pago no es valido.");
		return saleRepository.findByMethodPay(idMethodPay);
	}

	@Override
	public List<Sale> findByMonthAndYear(String month, int year) {
		if (!PastleyValidate.isChain(month))
			throw new PastleyException("El mes no es valido.");
		if (year <= 0)
			throw new PastleyException("El año debe ser mayor a cero.");
		return saleRepository.findByMonthAndYear(month, year);
	}

	@Override
	public List<Sale> findByMonthAndYearCurrent() {
		PastleyDate date = new PastleyDate();
		return findByMonthAndYear(date.currentMonthName(), date.currentYear());
	}

	@Override
	public List<Sale> findByStatuAll(boolean statu) {
		return saleRepository.findByStatu(statu);
	}

	@Override
	public List<Sale> findByRangeDateRegister(String start, String end) {
		String array_date[] = PastleyValidate.isRangeDateRegisterValidateDate(start, end);
		return saleRepository.findByRangeDateRegister(array_date[0], array_date[1]);
	}

	@Override
	public Sale save(Sale entity, int type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long id) {
		findById(id);
		List<SaleDetail> list = saleDetailService.findBySale(id);
		if (!list.isEmpty())
			throw new PastleyException("No se ha eliminado la venta con el id  " + id
					+ ", tiene asociado a " + list.size() + " detalles de ventas.");
		saleRepository.deleteById(id);
		try {
			if (findById(id) == null)
				return true;
		} catch (PastleyException e) {
			LOGGER.error("[delete(Long id)]", e);
			return true;
		}
		throw new PastleyException("No se ha eliminado la venta con el id " + id + ".");
	}
	
	@Override
	public ProductDTO findProductById(Long idProduct) {
		if (!PastleyValidate.isLong(idProduct))
			throw new PastleyException("El id del producto no es valido.");
		ProductDTO product = productFeignClient.findById(idProduct);
		if (product == null)
			throw new PastleyException("No se ha encontrado ningun producto con el id " + idProduct + ".");
		return product;
	}
	
	private Sale saveToSave(Sale entity, int type) {
		return entity;
	}

	private Sale saveToUpdate(Sale entity, int type) {
		return entity;
	}
}
