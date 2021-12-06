package com.pastley.application.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastley.application.exception.PastleyException;
import com.pastley.application.repository.ProductRepository;
import com.pastley.application.service.CategoryService;
import com.pastley.application.service.ProductService;
import com.pastley.application.validator.PastleyDate;
import com.pastley.application.validator.PastleyValidate;
import com.pastley.application.validator.ProductValidator;
import com.pastley.domain.Product;

/**
 * @project Pastley-Product.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	ProductRepository productRepository;
	@Autowired
	CategoryService categoryService;

	@Override
	public Product findById(Long id) {
		if (!PastleyValidate.isLong(id))
			throw new PastleyException("El id del producto no es valido.");
		Optional<Product> product = productRepository.findById(id);
		if (!product.isPresent())
			throw new PastleyException("No existe ningun producto con el id " + id + ".");
		return product.orElse(null);
	}

	public Product findByName(String name) {
		if (!PastleyValidate.isChain(name))
			throw new PastleyException("El nombre producto no es valido.");
		Product product = productRepository.findByName(name);
		if (product == null)
			throw new PastleyException("No existe ningun producto con el nombre " + name + ".");
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
	
	public List<Product> findBySuppliesAll(boolean supplies) {
		return productRepository.findBySupplies(supplies);
	}

	public List<Product> findByIdCategory(Long idCategory) {
		if (idCategory == null || idCategory <= 0)
			throw new PastleyException("El id de la categoria no es valido.");
		return productRepository.findByIdCategory(idCategory);
	}

	public List<Product> findByRangeDateRegister(String start, String end) {
		String arrayDate[] = PastleyValidate.isRangeDateRegisterValidateDate(start, end);
		return productRepository.findByRangeDateRegister(arrayDate[0], arrayDate[1]);
	}

	public Product save(Product entity, int type) {
		if (entity == null)
			throw new PastleyException("No se ha recibido el producto.");
		ProductValidator.validator(entity);
		String messageType = PastleyValidate.messageToSave(type, false);
		Product product = (entity.getId() != null && entity.getId() > 0) ? saveToUpdate(entity, type)
				: saveToSave(entity, type);
		if (type == 1 || type == 2)
			product.setCategory(categoryService.findById(entity.getCategory().getId()));
		product = productRepository.save(product);
		if (product == null)
			throw new PastleyException("No se ha " + messageType + " el producto.");
		return product;
	}

	@Override
	public boolean delete(Long id) {
		Product product = findById(id);
		if (product.isStatu())
			throw new PastleyException("No se ha eliminado el producto con el id " + id + ", tiene el estado activado.");
		if (product.getStock() > 0)
			throw new PastleyException("No se ha eliminado el producto con el id " + id + ", tiene el stock disponible.");
		productRepository.deleteById(id);
		try {
			if (findById(id) == null) {
				return true;
			}
		} catch (PastleyException e) {
			LOGGER.error("[delete(Long id)]", e);
			return true;
		}
		throw new PastleyException("No se ha eliminado el producto con el id " + id + ".");
	}

	public Product saveToSave(Product entity, int type) {
		if (!validateName(entity.getName()))
			throw new PastleyException("Ya existe un producto con el nombre " + entity.getName() + ".");
		PastleyDate date = new PastleyDate();
		uppercase(entity);
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
			throw new PastleyException("Ya existe un producto con el nombre " + entity.getName() + ".");
		PastleyDate date = new PastleyDate();
		uppercase(entity);
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
	
	private void uppercase(Product product) {
		product.setName(product.getName().toUpperCase());
	}
}
