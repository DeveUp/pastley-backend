package com.pastley.models.service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pastley.models.entity.Sale;
import com.pastley.models.entity.SaleDetail;
import com.pastley.models.feignclients.PersonFeignClient;
import com.pastley.models.feignclients.ProductFeignClient;
import com.pastley.models.model.PersonModel;
import com.pastley.models.model.ProductModel;
import com.pastley.models.model.UserModel;
import com.pastley.models.repository.SaleRepository;
import com.pastley.util.PastleyDate;
import com.pastley.util.PastleyInterface;
import com.pastley.util.PastleyValidate;
import com.pastley.util.exception.PastleyException;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors soleimygomez, leynerjoseoa, jhonatanbeltran.
 * @version 1.0.0.
 */
@Service
public class SaleService implements PastleyInterface<Long, Sale> {

	@Autowired
	private SaleRepository saleRepository;

	@Autowired
	private SaleDetailService saleDetailService;

	@Autowired
	private PersonFeignClient personFeignClient;

	@Autowired
	private ProductFeignClient productFeignClient;

	@Override
	public Sale findById(Long id) {
		if (id > 0) {
			Optional<Sale> sale = saleRepository.findById(id);
			if (sale != null) {
				return sale.orElse(null);
			} else {
				throw new PastleyException(HttpStatus.NOT_FOUND,
						"No se ha encontrado ninguna venta con el id " + id + ".");
			}
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id de la venta no es valido.");
		}
	}

	@Override
	public List<Sale> findAll() {
		return saleRepository.findAll();
	}
	
	public List<Sale> findByIdCustomerAll(Long idCustoner){
		if(idCustoner <= 0) 
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del cliente no es valido.");
		return saleRepository.findByIdCoustomer(idCustoner);
	}
	
	public List<Sale> findByIdMethodPayAll(Long idMethodPay){
		if(idMethodPay <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del metodo de pago no es valido.");
		return saleRepository.findByIdMethodPay(idMethodPay);
	}
	
	public List<Sale> findByMonthAndYear(String month, int year){
		if(!PastleyValidate.isChain(month)) 
			throw new PastleyException(HttpStatus.NOT_FOUND, "El mes no es valido.");
		if(year <=0) 
			throw new PastleyException(HttpStatus.NOT_FOUND, "El año debe ser mayor a cero.");
		return saleRepository.findByMonthAndYear(month, year);
	}
	
	public List<Sale> findByMonthAndYearCurrent(){
		PastleyDate date = new PastleyDate();
		return findByMonthAndYear(date.currentMonthName(), date.currentYear());
	}

	@Override
	public List<Sale> findByStatuAll(boolean statu) {
		return saleRepository.findByStatu(statu);
	}

	public List<Sale> findByRangeDateRegister(String start, String end) {
		String array_date[] = findByRangeDateRegisterValidateDate(start, end);
		return saleRepository.findByRangeDateRegister(array_date[0], array_date[1]);
	}

	@Override
	public Sale save(Sale entity) {
		return null;
	}
	
	public Sale save(Sale entity, byte type) {
		if (entity != null) {
			String message = entity.validate(false);
			String messageType = (type == 1) ? "registrar"
					: ((type == 2) ? "actualizar" : ((type == 3) ? "actualizar estado" : "n/a"));
			if (message == null) {
				Sale sale = null;
				if (entity.getId() != null && entity.getId() > 0) {
					sale = saveToUpdate(entity, type);
				} else {
					sale = saveToSave(entity, type);
				}
				sale = saleRepository.save(sale);
				if (sale != null) {
					return sale;
				} else {
					throw new PastleyException(HttpStatus.NOT_FOUND,
							"No se ha " + messageType + " la venta.");
				}
			} else {
				throw new PastleyException(HttpStatus.NOT_FOUND,
						"No se ha " + messageType + " la venta, " + message + ".");
			}
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha recibido la venta.");
		}
	}

	public Sale saveToSave(Sale entity, byte type) {
		return null;
	}

	public Sale saveToUpdate(Sale entity, byte type) {
		return null;
	}

	@Override
	public boolean delete(Long id) {
		findById(id);
		List<SaleDetail> list = saleDetailService.findBySale(id);
		if (list.isEmpty()) {
			saleRepository.deleteById(id);
			try {
				if (findById(id) == null) {
					return true;
				}
			} catch (PastleyException e) {
				return true;
			}
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha eliminado la venta con el id  " + id
					+ ", tiene asociado a " + list.size() + " detalles de ventas.");
		}
		throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha eliminado la venta con el id " + id + ".");
	}

	public PersonModel findPersonByDocument(Long documentPerson) {
		if (documentPerson > 0) {
			PersonModel person = personFeignClient.findByDocument(documentPerson);
			if (person == null) {
				throw new PastleyException(HttpStatus.NOT_FOUND,
						"No se ha encontrado ninguna persona con el documento " + documentPerson + ".");
			}
			return person;
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND, "El documento de la persona no es valido.");
		}
	}

	public UserModel findUserById(Long idUser) {
		if (idUser > 0) {
			return new UserModel();
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del usuario no es valido.");
		}
	}

	public ProductModel findProductById(Long idProduct) {
		if (idProduct > 0) {
			ProductModel product = productFeignClient.findById(idProduct);
			if (product == null) {
				throw new PastleyException(HttpStatus.NOT_FOUND,
						"No se ha encontrado ningun producto con el id " + idProduct + ".");
			}
			return product;
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del producto no es valido.");
		}
	}
	
	private String[] findByRangeDateRegisterValidateDate(String start, String end) {
		if (PastleyValidate.isChain(start) && PastleyValidate.isChain(end)) {
			PastleyDate date = new PastleyDate();
			try {
				String array_date[] = { date.formatToDateTime(date.convertToDate(start.replaceAll("-", "/")), null),
						date.formatToDateTime(date.convertToDate(end.replaceAll("-", "/")), null) };
				return array_date;
			} catch (ParseException e) {
				throw new PastleyException(HttpStatus.NOT_FOUND,
						"El formato permitido para las fechas es: 'Año-Mes-Dia'.");
			}
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha recibido la fecha inicio o la fecha fin.");
		}
	}
}
