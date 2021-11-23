package com.pastley.model.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pastley.model.entity.Category;
import com.pastley.model.entity.Product;
import com.pastley.model.repository.CategoryRepository;
import com.pastley.util.PastleyDate;
import com.pastley.util.PastleyInterface;
import com.pastley.util.PastleyValidate;
import com.pastley.util.exception.PastleyException;

/**
 * @project Pastley-Product.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Service
public class CategoryService implements PastleyInterface<Long, Category> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ProductService productService;

	@Override
	public Category findById(Long id) {
		if (id <= 0)
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id de la categoria no es valido.");
		Optional<Category> category = categoryRepository.findById(id);
		if (!category.isPresent())
			throw new PastleyException(HttpStatus.NOT_FOUND, "No existe ninguna categoria con el id " + id + ".");
		return category.orElse(null);
	}

	public Category findByName(String name) {
		if (!PastleyValidate.isChain(name))
			throw new PastleyException(HttpStatus.NOT_FOUND, "El nombre de la categoria no es valido.");
		Category category = categoryRepository.findByName(name);
		if (category == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No existe ninguna categoria con el nombre " + name + ".");
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

	@Override
	public Category save(Category entity) {
		return null;
	}

	public Category save(Category entity, int type) {
		if (entity == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha recibido la categoria.");
		String message = entity.validate();
		String messageType = PastleyValidate.messageToSave(type, false);
		if (message != null)
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha " + messageType + " la categoria, " + message + ".");
		Category category = (entity.getId() != null && entity.getId() > 0) ? saveToUpdate(entity, type)
				: saveToSave(entity, type);
		category = categoryRepository.save(category);
		if (category == null)
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " la categoria.");
		return category;
	}

	@Override
	public boolean delete(Long id) {
		findById(id);
		List<Product> list = productService.findByIdCategory(id);
		if (!list.isEmpty())
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha eliminado la categoria con el id  " + id
					+ ", tiene asociado " + list.size() + " productos.");
		categoryRepository.deleteById(id);
		try {
			if (findById(id) == null) {
				return true;
			}
		} catch (PastleyException e) {
			LOGGER.error("[delete]: " + e.getMessage());
			return true;
		}
		throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha eliminado la categoria con el id " + id + ".");
	}

	private Category saveToSave(Category entity, int type) {
		if (!validateName(entity.getName()))
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"Ya existe una categoria con el nombre " + entity.getName() + ".");
		PastleyDate date = new PastleyDate();
		entity.uppercase();
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
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"Ya existe una categoria con el nombre " + entity.getName() + ".");
		PastleyDate date = new PastleyDate();
		entity.uppercase();
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
			LOGGER.error("[validateName(String name)]: " + e.getMessage());
		}
		return (category == null) ? true : false;
	}

	private boolean testName(String nameA, String nameB) {
		return (!nameA.equalsIgnoreCase(nameB)) ? validateName(nameA) : true;
	}
}
