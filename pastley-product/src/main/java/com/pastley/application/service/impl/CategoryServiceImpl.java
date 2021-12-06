package com.pastley.application.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastley.application.exception.PastleyException;
import com.pastley.application.repository.CategoryRepository;
import com.pastley.application.service.CategoryService;
import com.pastley.application.service.ProductService;
import com.pastley.application.validator.CategoryValidator;
import com.pastley.application.validator.PastleyDate;
import com.pastley.application.validator.PastleyValidate;
import com.pastley.domain.Category;
import com.pastley.domain.Product;

/**
 * @project Pastley-Product.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ProductService productService;

	@Override
	public Category findById(Long id) {
		if (!PastleyValidate.isLong(id))
			throw new PastleyException("El id de la categoria no es valido.");
		Optional<Category> category = categoryRepository.findById(id);
		if (!category.isPresent())
			throw new PastleyException("No existe ninguna categoria con el id " + id + ".");
		return category.orElse(null);
	}

	public Category findByName(String name) {
		if (!PastleyValidate.isChain(name))
			throw new PastleyException("El nombre de la categoria no es valido.");
		Category category = categoryRepository.findByName(name);
		if (category == null)
			throw new PastleyException("No existe ninguna categoria con el nombre " + name + ".");
		return category;
	}

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	public List<Category> findByStatuAll(boolean statu) {
		return categoryRepository.findByStatu(statu);
	}

	public List<Category> findByRangeDateRegister(String start, String end) {
		String arrayDate[] = PastleyValidate.isRangeDateRegisterValidateDate(start, end);
		return categoryRepository.findByRangeDateRegister(arrayDate[0], arrayDate[1]);
	}

	public Category save(Category entity, int type) {
		if (entity == null)
			throw new PastleyException("No se ha recibido la categoria.");
		CategoryValidator.validator(entity);
		String messageType = PastleyValidate.messageToSave(type, false);
		Category category = (entity.getId() != null && entity.getId() > 0) ? saveToUpdate(entity, type)
				: saveToSave(entity, type);
		category = categoryRepository.save(category);
		if (category == null)
			throw new PastleyException("No se ha " + messageType + " la categoria.");
		return category;
	}

	@Override
	public boolean delete(Long id) {
		findById(id);
		List<Product> list = productService.findByIdCategory(id);
		if (!list.isEmpty())
			throw new PastleyException("No se ha eliminado la categoria con el id  " + id
					+ ", tiene asociado " + list.size() + " productos.");
		categoryRepository.deleteById(id);
		try {
			if (findById(id) == null) {
				return true;
			}
		} catch (PastleyException e) {
			LOGGER.error("[delete(Long id)]", e);
			return true;
		}
		throw new PastleyException("No se ha eliminado la categoria con el id " + id + ".");
	}

	private Category saveToSave(Category entity, int type) {
		if (!validateName(entity.getName()))
			throw new PastleyException("Ya existe una categoria con el nombre " + entity.getName() + ".");
		PastleyDate date = new PastleyDate();
		uppercase(entity);
		entity.setId(0L);
		entity.setDateRegister(date.currentToDateTime(null));
		entity.setDateUpdate(null);
		entity.setStatu(true);
		return entity;
	}

	private Category saveToUpdate(Category entity, int type) {
		Category category = null;
		if (type != 3)
			category = findById(entity.getId());
		if (!testName(entity.getName(), (type == 3 ? entity.getName() : category.getName())))
			throw new PastleyException("Ya existe una categoria con el nombre " + entity.getName() + ".");
		PastleyDate date = new PastleyDate();
		uppercase(entity);
		entity.setDateRegister((type == 3) ? entity.getDateRegister() : category.getDateRegister());
		entity.setDateUpdate(date.currentToDateTime(null));
		entity.setStatu((type == 3) ? !entity.isStatu() : entity.isStatu());
		return entity;
	}

	private boolean validateName(String name) {
		Category category = null;
		try {
			category = findByName(name);
		} catch (PastleyException e) {
			LOGGER.error("[validateName(String name)]", e);
		}
		return (category == null) ? true : false;
	}

	private boolean testName(String nameA, String nameB) {
		return (!nameA.equalsIgnoreCase(nameB)) ? validateName(nameA) : true;
	}
	
	private void uppercase(Category category) {
		category.setName(category.getName().toUpperCase());
	}
}
