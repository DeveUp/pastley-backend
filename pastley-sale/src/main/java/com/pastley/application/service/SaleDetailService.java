package com.pastley.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pastley.application.repository.SaleDetailRepository;
import com.pastley.domain.Cart;
import com.pastley.domain.SaleDetail;
import com.pastley.infrastructure.config.PastleyInterface;
import com.pastley.infrastructure.exception.PastleyException;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Service
public class SaleDetailService implements PastleyInterface<Long, SaleDetail> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SaleDetailService.class);

	@Autowired
	SaleDetailRepository saleDetailRepository;

	@Autowired
	SaleService saleService;

	@Autowired
	CartService cartService;

	/**
	 * Method that allows to know a sale detail by its id.
	 * 
	 * @param id, Represents the identifier of the detail sale.
	 * @return SaleDetail.
	 */
	@Override
	public SaleDetail findById(Long id) {
		if (id <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del del detalle de venta no es valido.");
		Optional<SaleDetail> saleDetail = saleDetailRepository.findById(id);
		if (!saleDetail.isPresent())
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha encontrado ningun detalle de venta con el id " + id + ".");
		return saleDetail.orElse(null);
	}

	/**
	 * Method that allows to know all the details of registered sales.
	 * 
	 * @return List of SaleDetail.
	 */
	@Override
	public List<SaleDetail> findAll() {
		return saleDetailRepository.findAll();
	}

	/**
	 * N/A.
	 */
	@Override
	public List<SaleDetail> findByStatuAll(boolean statu) {
		return new ArrayList<>();
	}

	/**
	 * Method that allows knowing all the sale details by the id of a sale.
	 * 
	 * @param idSale, Represents the id of the sale detail.
	 * @return List of SaleDetail.
	 */
	public List<SaleDetail> findBySale(Long idSale) {
		saleService.findById(idSale);
		return saleDetailRepository.findByIdSale(idSale);
	}

	@Override
	public SaleDetail save(SaleDetail entity) {
		if (entity != null) {
			String message = entity.validate();
			if (message == null) {
				String messageType = (entity.getId() <= 0) ? "registrar" : "actualizar";
				SaleDetail saleDetail = null;
				Cart cart = cartService.findById(entity.getCart().getId());
				if (cart != null) {
					entity.setCart(cart);
					saleDetail = saleDetailRepository.save(saleDetail);
					if (saleDetail != null) {
						return saleDetail;
					} else {
						throw new PastleyException(HttpStatus.NOT_FOUND,
								"No se ha " + messageType + " el detalle de venta.");
					}
				} else {
					throw new PastleyException(HttpStatus.NOT_FOUND,
							"No se ha " + messageType
									+ " el detalle de venta, no existe ningun producto en el carrito con el id "
									+ entity.getCart().getId() + ".");
				}
			} else {
				throw new PastleyException(HttpStatus.NOT_FOUND,
						"No se ha procesado el detalle de venta," + message + ".");
			}
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha recibido el detalle de venta.");
		}
	}

	/**
	 * Method that allows you to delete a sale detail.
	 * 
	 * @param id, Represents the identifier of the detail sale.
	 */
	@Override
	public boolean delete(Long id) {
		findById(id);
		saleDetailRepository.deleteById(id);
		try {
			if (findById(id) == null) {
				return true;
			}
		} catch (PastleyException e) {
			LOGGER.error("[delete(Long id)]", e);
			return true;
		}
		throw new PastleyException(HttpStatus.NOT_FOUND,
				"No se ha eliminado el detalle de venta con el id " + id + ".");
	}
}
