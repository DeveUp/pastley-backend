package com.pastley.application.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pastley.application.repository.SaleRepository;
import com.pastley.domain.Sale;
import com.pastley.domain.SaleDetail;
import com.pastley.infrastructure.config.PastleyDate;
import com.pastley.infrastructure.config.PastleyInterface;
import com.pastley.infrastructure.config.PastleyValidate;
import com.pastley.infrastructure.exception.PastleyException;
import com.pastley.infrastructure.dto.PersonModel;
import com.pastley.infrastructure.dto.ProductDTO;
import com.pastley.infrastructure.dto.UserModel;
import com.pastley.infrastructure.feign.PersonFeignClient;
import com.pastley.infrastructure.feign.ProductFeignClient;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Service
public class SaleService implements PastleyInterface<Long, Sale> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SaleService.class);

	@Autowired
	SaleRepository saleRepository;
	@Autowired
	SaleDetailService saleDetailService;
	@Autowired
	PersonFeignClient personFeignClient;
	@Autowired
	ProductFeignClient productFeignClient;

	@Override
	public Sale findById(Long id) {
		if (id <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id de la venta no es valido.");
		Optional<Sale> sale = saleRepository.findById(id);
		if (!sale.isPresent())
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha encontrado ninguna venta con el id " + id + ".");
		return sale.orElse(null);
	}

	@Override
	public List<Sale> findAll() {
		return saleRepository.findAll();
	}

	public List<Sale> findByIdCustomerAll(Long idCustoner) {
		if (idCustoner <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del cliente no es valido.");
		return saleRepository.findByIdCoustomer(idCustoner);
	}

	public List<Sale> findByIdMethodPayAll(Long idMethodPay) {
		if (idMethodPay <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del metodo de pago no es valido.");
		return saleRepository.findByIdMethodPay(idMethodPay);
	}

	public List<Sale> findByMonthAndYear(String month, int year) {
		if (!PastleyValidate.isChain(month))
			throw new PastleyException(HttpStatus.NOT_FOUND, "El mes no es valido.");
		if (year <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El año debe ser mayor a cero.");
		return saleRepository.findByMonthAndYear(month, year);
	}

	public List<Sale> findByMonthAndYearCurrent() {
		PastleyDate date = new PastleyDate();
		return findByMonthAndYear(date.currentMonthName(), date.currentYear());
	}

	@Override
	public List<Sale> findByStatuAll(boolean statu) {
		return saleRepository.findByStatu(statu);
	}

	public List<Sale> findByRangeDateRegister(String start, String end) {
		String array_date[] = PastleyValidate.isRangeDateRegisterValidateDate(start, end);
		return saleRepository.findByRangeDateRegister(array_date[0], array_date[1]);
	}

	@Override
	public Sale save(Sale entity) {
		return null;
	}

	public Sale save(Sale entity, int type) {
		if (entity == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha recibido la venta.");
		String message = entity.validate();
		String messageType = PastleyValidate.messageToSave(type, false);
		if (message != null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " la venta, " + message + ".");
		Sale sale = (entity.getId() != null && entity.getId() > 0) ? saveToUpdate(entity, type)
				: saveToSave(entity, type);
		sale = saleRepository.save(sale);
		if (sale == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " la venta.");
		return sale;
	}

	@Override
	public boolean delete(Long id) {
		findById(id);
		List<SaleDetail> list = saleDetailService.findBySale(id);
		if (!list.isEmpty())
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha eliminado la venta con el id  " + id
					+ ", tiene asociado a " + list.size() + " detalles de ventas.");
		saleRepository.deleteById(id);
		try {
			if (findById(id) == null) {
				return true;
			}
		} catch (PastleyException e) {
			LOGGER.error("[delete(Long id)]", e);
			return true;
		}
		throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha eliminado la venta con el id " + id + ".");
	}

	public PersonModel findPersonByDocument(Long documentPerson) {
		if(documentPerson <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El documento de la persona no es valido.");
		PersonModel person = personFeignClient.findByDocument(documentPerson);
		if (person == null) {
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha encontrado ninguna persona con el documento " + documentPerson + ".");
		}
		return person;
	}

	public UserModel findUserById(Long idUser) {
		if (idUser <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del usuario no es valido.");
		return new UserModel();
	}

	public ProductDTO findProductById(Long idProduct) {
		if (idProduct <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del producto no es valido.");
		ProductDTO product = productFeignClient.findById(idProduct);
		if (product == null) {
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha encontrado ningun producto con el id " + idProduct + ".");
		}
		return product;
	}

	private Sale saveToSave(Sale entity, int type) {
		return entity;
	}

	private Sale saveToUpdate(Sale entity, int type) {
		return entity;
	}
}
