package com.pastley.models.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pastley.util.PastleyDate;
import com.pastley.util.PastleyInterface;
import com.pastley.util.PastleyValidate;
import com.pastley.util.exception.PastleyException;
import com.pastley.models.entity.Cart;
import com.pastley.models.model.ProductModel;
import com.pastley.models.repository.CartRepository;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors soleimygomez, leynerjoseoa, jhonatanbeltran.
 * @version 1.0.0.
 */
@Service
public class CartService implements PastleyInterface<Long, Cart> {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private SaleService saleService;

	@Override
	public Cart findById(Long id) {
		if (id > 0) {
			Optional<Cart> cart = cartRepository.findById(id);
			if (cart.isPresent()) {
				cart.get().calculate();
				return cart.orElse(null);
			} else {
				throw new PastleyException(HttpStatus.NOT_FOUND,
						"No se ha encontrado ningun producto en el carrito con el id " + id + ".");
			}
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del producto del carrito no es valido.");
		}
	}

	public Cart findByCustomerAndProductAndStatu(boolean statu, Long idCustomer, Long idProduct) {
		if (idCustomer > 0) {
			if (idProduct > 0) {
				Cart cart = cartRepository.findByCustomerAndProductAndStatu(statu, idCustomer, idProduct);
				if (cart != null) {
					return cart;
				}
				throw new PastleyException(HttpStatus.NOT_FOUND,
						"No se ha encontrado ningun producto de carrito con el id del cliente " + idCustomer
								+ ", id producto " + idProduct + " y estado " + statu + ".");
			} else {
				throw new PastleyException(HttpStatus.NOT_FOUND, "El id del producto no es valido.");
			}
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del cliente no es valido.");
		}
	}

	@Override
	public List<Cart> findAll() {
		return calculate(cartRepository.findAll());
	}

	@Override
	public List<Cart> findByStatuAll(boolean statu) {
		return calculate(cartRepository.findByStatu(statu));
	}

	public List<Cart> findByCustomer(Long idCustomer) {
		if (idCustomer > 0) {
			return calculate(cartRepository.findByIdCustomer(idCustomer));
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del cliente no es valido.");
		}
	}

	public List<Cart> findByCustomerAndStatus(Long idCustomer, boolean statu) {
		if (idCustomer > 0) {
			return calculate(cartRepository.findByCustomerAndStatus(idCustomer, statu));
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del cliente no es valido.");
		}
	}

	public List<Cart> findByCustomerAndProduct(Long idCustomer, Long idProduct) {
		if (idCustomer > 0) {
			if (idProduct > 0) {
				return calculate(cartRepository.findByCustomerAndProduct(idCustomer, idProduct));
			} else {
				throw new PastleyException(HttpStatus.NOT_FOUND, "El id del producto no es valido.");
			}
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del cliente no es valido.");
		}
	}

	public List<Cart> findByProductAndStatus(Long idProduct, boolean statu) {
		saleService.findProductById(idProduct);
		return calculate(cartRepository.findByProductAndStatus(idProduct, statu));
	}

	public List<Cart> findByRangeDateRegister(String start, String end) {
		String array_date[] = findByRangeDateRegisterValidateDate(start, end);
		return calculate(cartRepository.findByRangeDateRegister(array_date[0], array_date[1]));
	}

	public List<Cart> findByRangeDateRegisterAndCustomer(Long idCustomer, String start, String end) {
		String array_date[] = findByRangeDateRegisterValidateDate(start, end);
		return calculate(cartRepository.findByRangeDateRegisterAndCustomer(idCustomer, array_date[0], array_date[1]));
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

	@Override
	public Cart save(Cart entity) {
		return null;
	}

	public Cart save(Cart entity, byte type) {
		if (entity != null) {
			String message = entity.validate(false, false);
			String messageType = (type == 1) ? "registrado"
					: ((type == 2) ? "actualizado"
							: ((type == 3) ? "actualizado el estado" : ((type == 4) ? "actualizando cantidad" : "n/a")));
			if (message == null) {
				ProductModel product = null;
				try {
					product = saleService.findProductById(entity.getIdProduct());
				} catch (Exception e) {
				}
				if (product != null) {
					Cart cart = null;
					if (entity.getId() != null && entity.getId() > 0) {
						cart = saveToUpdate(entity, type);
					} else {
						cart = saveToSave(entity, type);
					}
					cart.setPrice(PastleyValidate.bigIntegerHigherZero(entity.getPrice()) ? entity.getPrice()
							: PastleyValidate.bigIntegerHigherZero(product.getPrice()) ? product.getPrice()
									: BigInteger.ZERO);
					if (PastleyValidate.bigIntegerHigherZero(cart.getPrice())) {
						cart.setDiscount(PastleyValidate.isChain(cart.getDiscount()) ? cart.getDiscount()
								: PastleyValidate.isChain(product.getDiscount()) ? product.getDiscount() : "0");
						cart.setVat(PastleyValidate.isChain(cart.getVat()) ? cart.getVat()
								: PastleyValidate.isChain(product.getVat()) ? product.getVat() : "0");
						cart.calculate();
						cart = cartRepository.save(cart);
						if (cart != null) {
							cart.calculate();
							return cart;
						} else {
							throw new PastleyException(HttpStatus.NOT_FOUND,
									"No se ha " + messageType + " el producto carrito.");
						}
					} else {
						throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType
								+ " el producto carrito, el precio del producto debe ser mayor a cero.");
					}
				} else {
					throw new PastleyException(HttpStatus.NOT_FOUND,
							"No se ha " + messageType
									+ " el producto carrito, no se ha encontrado ningun producto con el id "
									+ entity.getIdProduct() + ".");
				}
			} else {
				throw new PastleyException(HttpStatus.NOT_FOUND,
						"No se ha " + messageType + " el producto carrito, " + message);
			}
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha recibido el cart.");
		}
	}

	private Cart saveToSave(Cart entity, byte type) {
		try {
			findByCustomerAndProductAndStatu(true, entity.getIdCustomer(), entity.getIdProduct());
		} catch (Exception e) {
			PastleyDate date = new PastleyDate();
			entity.setId(0L);
			entity.setDateRegister(date.currentToDateTime(null));
			entity.setDateUpdate(null);
			entity.setStatu(true);
			entity.setCount(entity.getCount() <= 0 ? 1 : entity.getCount());
			return entity;
		}
		throw new PastleyException(HttpStatus.NOT_FOUND, "El cliente id " + entity.getIdCustomer()
				+ " ya tiene agregado en el carrito el producto con el id " + entity.getIdProduct() + ".");
	}

	private Cart saveToUpdate(Cart entity, byte type) {
		Cart cart = findById(entity.getId());
		if (cart != null) {
			PastleyDate date = new PastleyDate();
			entity.setDateRegister(cart.getDateRegister());
			entity.setCount((type == 4) ? entity.getCount()
					: (cart.getCount() <= 0) ? ((cart.getCount() <= 0) ? 1 : cart.getCount()) : entity.getCount());
			entity.setStatu((type == 4) ? cart.isStatu() : (type == 3) ? !entity.isStatu() : entity.isStatu());
			entity.setDateUpdate(date.currentToDateTime(null));
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha encontrado ningun producto carrito con el id " + entity.getId() + ".");
		}
		return entity;

	}

	@Override
	public boolean delete(Long id) {
		Cart cart = findById(id);
		if (cart.isStatu()) {
			cartRepository.deleteById(id);
			try {
				if (findById(id) == null) {
					return true;
				}
			} catch (PastleyException e) {
				return true;
			}
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha eliminado el producto del carito con el id " + id + ", ya se realizo la venta.");
		}
		throw new PastleyException(HttpStatus.NOT_FOUND,
				"No se ha eliminado el producto del carito con el id " + id + ".");
	}

	public List<Cart> calculate(List<Cart> list) {
		if (!list.isEmpty())
			list.forEach((e) -> {
				e.calculate();
			});
		return list;
	}
}
