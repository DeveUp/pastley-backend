package com.pastley.application.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pastley.application.repository.ProductRepository;
import com.pastley.domain.Product;
import com.pastley.infrastructure.config.PastleyDate;
import com.pastley.infrastructure.config.PastleyInterface;
import com.pastley.infrastructure.config.PastleyValidate;
import com.pastley.infrastructure.exception.PastleyException;

@Service
public class ProductService implements PastleyInterface<Long, Product> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CategoryService categoryService;

	@Override
	public Product findById(Long id) {
		if (id <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del producto no es valido.");
		Optional<Product> product = productRepository.findById(id);
		if (!product.isPresent())
			throw new PastleyException(HttpStatus.NOT_FOUND, "No existe ningun producto con el id " + id + ".");
		return product.orElse(null);
	}

	public Product findByName(String name) {
		if (!PastleyValidate.isChain(name))
			throw new PastleyException(HttpStatus.NOT_FOUND, "El nombre producto no es valido.");
		Product product = productRepository.findByName(name);
		if (product == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No existe ningun producto con el nombre " + name + ".");
		return product;
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public List<Product> findProductByPromotionAll() {
		return productRepository.findProductByPromotion();
	}

	public List<Product> findByStatuAll(boolean statu) {
		return productRepository.findByStatu(statu);
	}

	public List<Product> findByIdCategory(Long idCategory) {
		if (idCategory == null || idCategory <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id de la categoria no es valido.");
		return productRepository.findByIdCategory(idCategory);
	}

	public List<Product> findByRangeDateRegister(String start, String end) {
		String arrayDate[] = PastleyValidate.isRangeDateRegisterValidateDate(start, end);
		return productRepository.findByRangeDateRegister(arrayDate[0], arrayDate[1]);
	}

	@Override
	public Product save(Product entity) {
		return null;
	}

	public Product save(Product entity, int type) {
		if (entity == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha recibido el producto.");
		String message = entity.validate();
		String messageType = PastleyValidate.messageToSave(type, false);
		if (message != null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " el producto, " + message);
		Product product = (entity.getId() != null && entity.getId() > 0) ? saveToUpdate(entity, type)
				: saveToSave(entity, type);
		if (type == 1 || type == 2)
			product.setCategory(categoryService.findById(entity.getCategory().getId()));
		product = productRepository.save(product);
		if (product == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " el producto.");
		return product;
	}

	@Override
	public boolean delete(Long id) {
		Product product = findById(id);
		if (product.isStatu())
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha eliminado el producto con el id " + id + ", tiene el estado activado.");
		if (product.getStock() > 0)
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha eliminado el producto con el id " + id + ", tiene el stock disponible.");
		productRepository.deleteById(id);
		try {
			if (findById(id) == null) {
				return true;
			}
		} catch (PastleyException e) {
			LOGGER.error("[delete(Long id)]", e);
			return true;
		}
		throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha eliminado el producto con el id " + id + ".");
	}

	public Product saveToSave(Product entity, int type) {
		if (!validateName(entity.getName()))
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"Ya existe un producto con el nombre " + entity.getName() + ".");
		PastleyDate date = new PastleyDate();
		entity.uppercase();
		entity.setId(0L);
		entity.setDateRegister(date.currentToDateTime(null));
		entity.setDateUpdate(null);
		entity.setStatu(true);
		return entity;
	}

	public Product saveToUpdate(Product entity, int type) {
		Product product = null;
		if (type != 3)
			product = findById(entity.getId());
		if (!testName(entity.getName(), (type == 3 ? entity.getName() : product.getName())))
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"Ya existe un producto con el nombre " + entity.getName() + ".");
		PastleyDate date = new PastleyDate();
		entity.uppercase();
		entity.setDateRegister((type == 3) ? entity.getDateRegister() : product.getDateRegister());
		entity.setDateUpdate(date.currentToDateTime(null));
		entity.setStatu((type == 3) ? !entity.isStatu() : entity.isStatu());
		return entity;
	}

	private boolean validateName(String name) {
		Product product = null;
		try {
			product = findByName(name);
		} catch (PastleyException e) {
			LOGGER.error("[validateName(String name)]", e);
		}
		return (product == null) ? true : false;
	}

	private boolean testName(String nameA, String nameB) {
		return (!nameA.equalsIgnoreCase(nameB)) ? validateName(nameA) : true;
	}
}
