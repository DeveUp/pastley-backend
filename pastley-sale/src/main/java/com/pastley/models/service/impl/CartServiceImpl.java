package com.pastley.models.service.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastley.models.dto.ProductDTO;
import com.pastley.models.entity.Cart;
import com.pastley.models.entity.calculate.CartCalculate;
import com.pastley.models.entity.validator.CartValidator;
import com.pastley.models.repository.CartRepository;
import com.pastley.models.service.CartService;
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
public class CartServiceImpl implements CartService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);

	@Autowired
	CartRepository cartRepository;

	@Autowired
	SaleService saleService;

	@Override
	public Cart findById(Long id) {
		if (!PastleyValidate.isLong(id))
			throw new PastleyException("El id del producto del carrito no es valido.");
		Optional<Cart> cart = cartRepository.findById(id);
		if (!cart.isPresent())
			throw new PastleyException("No se ha encontrado ningun producto en el carrito con el id " + id + ".");
		return CartCalculate.calculate(cart.orElse(null));
	}

	@Override
	public Cart findByCustomerAndProductAndStatu(boolean statu, Long idCustomer, Long idProduct) {
		testCustomer(idCustomer);
		testProduct(idProduct);
		Cart cart = cartRepository.findByCustomerAndProductAndStatu(statu, idCustomer, idProduct);
		if (cart == null)
			throw new PastleyException("No se ha encontrado ningun producto de carrito con el id del cliente "
					+ idCustomer + ", id producto " + idProduct + " y estado " + statu + ".");
		return cart;
	}

	@Override
	public List<Cart> findAll() {
		return calculate(cartRepository.findAll());
	}

	@Override
	public List<Cart> findByStatuAll(boolean statu) {
		return calculate(cartRepository.findByStatu(statu));
	}

	@Override
	public List<Cart> findByCustomer(Long idCustomer) {
		testCustomer(idCustomer);
		return calculate(cartRepository.findByIdCustomer(idCustomer));
	}

	@Override
	public List<Cart> findByCustomerAndStatus(Long idCustomer, boolean statu) {
		testCustomer(idCustomer);
		return calculate(cartRepository.findByCustomerAndStatus(idCustomer, statu));
	}

	@Override
	public List<Cart> findByCustomerAndProduct(Long idCustomer, Long idProduct) {
		testCustomer(idCustomer);
		testProduct(idProduct);
		return calculate(cartRepository.findByCustomerAndProduct(idCustomer, idProduct));
	}

	@Override
	public List<Cart> findByProductAndStatus(Long idProduct, boolean statu) {
		saleService.findProductById(idProduct);
		return calculate(cartRepository.findByProductAndStatus(idProduct, statu));
	}

	@Override
	public List<Cart> findByRangeDateRegister(String start, String end) {
		String array_date[] = PastleyValidate.isRangeDateRegisterValidateDate(start, end);
		return calculate(cartRepository.findByRangeDateRegister(array_date[0], array_date[1]));
	}

	@Override
	public List<Cart> findByRangeDateRegisterAndCustomer(Long idCustomer, String start, String end) {
		testCustomer(idCustomer);
		String array_date[] = PastleyValidate.isRangeDateRegisterValidateDate(start, end);
		return calculate(cartRepository.findByRangeDateRegisterAndCustomer(idCustomer, array_date[0], array_date[1]));
	}

	@Override
	public Cart save(Cart entity, int type) {
		CartValidator.validator(entity);
		String messageType = saveToMessage(type);
		ProductDTO product = saleService.findProductById(entity.getIdProduct());
		Cart cart = PastleyValidate.isLong(entity.getId()) ? saveToUpdate(entity, type) : saveToSave(entity, type);
		cart.setPrice(PastleyValidate.bigIntegerHigherZero(entity.getPrice()) ? entity.getPrice()
				: PastleyValidate.bigIntegerHigherZero(product.getPrice()) ? product.getPrice() : BigInteger.ZERO);
		if (!PastleyValidate.bigIntegerHigherZero(cart.getPrice()))
			throw new PastleyException(
					"No se ha " + messageType + " el producto carrito, el precio del producto debe ser mayor a cero.");
		cart.setDiscount(PastleyValidate.isChain(cart.getDiscount()) ? cart.getDiscount()
				: PastleyValidate.isChain(product.getDiscount()) ? product.getDiscount() : "0");
		cart.setVat(PastleyValidate.isChain(cart.getVat()) ? cart.getVat()
				: PastleyValidate.isChain(product.getVat()) ? product.getVat() : "0");
		cart = cartRepository.save(CartCalculate.calculate(cart));
		if (cart == null)
			throw new PastleyException("No se ha " + messageType + " el producto carrito.");
		return CartCalculate.calculate(cart);
	}

	@Override
	public boolean delete(Long id) {
		Cart cart = findById(id);
		if (!cart.isStatu())
			throw new PastleyException(
					"No se ha eliminado el producto del carito con el id " + id + ", ya se realizo la venta.");
		cartRepository.deleteById(id);
		try {
			if (findById(id) == null)
				return true;
		} catch (PastleyException e) {
			LOGGER.error("[delete(Long id)]", e);
			return true;
		}
		throw new PastleyException("No se ha eliminado el producto del carito con el id " + id + ".");
	}

	private Cart saveToSave(Cart entity, int type) {
		try {
			findByCustomerAndProductAndStatu(true, entity.getIdCustomer(), entity.getIdProduct());
		} catch (Exception e) {
			LOGGER.error("[saveToSave(Cart entity, int type)]", e);
			PastleyDate date = new PastleyDate();
			entity.setId(0L);
			entity.setDateRegister(date.currentToDateTime(null));
			entity.setDateUpdate(null);
			entity.setStatu(true);
			entity.setCount(entity.getCount() <= 0 ? 1 : entity.getCount());
			return entity;
		}
		throw new PastleyException("El cliente id " + entity.getIdCustomer()
				+ " ya tiene agregado en el carrito el producto con el id " + entity.getIdProduct() + ".");
	}

	private Cart saveToUpdate(Cart entity, int type) {
		Cart cart = findById(entity.getId());
		if (cart == null)
			throw new PastleyException("No se ha encontrado ningun producto carrito con el id " + entity.getId() + ".");
		PastleyDate date = new PastleyDate();
		entity.setDateRegister(cart.getDateRegister());
		entity.setCount((type == 4) ? entity.getCount()
				: (cart.getCount() <= 0) ? ((cart.getCount() <= 0) ? 1 : cart.getCount()) : entity.getCount());
		entity.setStatu((type == 4) ? cart.isStatu() : (type == 3) ? !entity.isStatu() : entity.isStatu());
		entity.setDateUpdate(date.currentToDateTime(null));
		return entity;
	}

	private List<Cart> calculate(List<Cart> list) {
		if (!list.isEmpty())
			list.forEach((e) -> {
				e = CartCalculate.calculate(e);
			});
		return list;
	}

	private void testCustomer(Long idCustomer) {
		if (!PastleyValidate.isLong(idCustomer))
			throw new PastleyException("El id del cliente no es valido.");
	}

	private void testProduct(Long idProduct) {
		if (!PastleyValidate.isLong(idProduct))
			throw new PastleyException("El id del producto no es valido.");
	}

	private String saveToMessage(int type) {
		String message = null;
		switch (type) {
		case 1:
			message = "registrado";
			break;
		case 2:
			message = "actualizado";
			break;
		case 3:
			message = "actualizado el estado";
			break;
		case 4:
			message = "actualizando cantidad";
			break;
		default:
			message = "n/a";
			break;
		}
		return message;
	}
}
