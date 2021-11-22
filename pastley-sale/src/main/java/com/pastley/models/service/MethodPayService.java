package com.pastley.models.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pastley.models.entity.MethodPay;
import com.pastley.models.model.StatisticModel;
import com.pastley.models.repository.MethodPayRepository;
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
public class MethodPayService implements PastleyInterface<Long, MethodPay> {

	@Autowired
	private MethodPayRepository methodPayRepository;

	@Override
	public MethodPay findById(Long id) {
		if (id > 0) {
			Optional<MethodPay> method = methodPayRepository.findById(id);
			if (method.isPresent()) {
				return method.orElse(null);
			}
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha encontrado ningun metodo de pago con el id " + id + ".");
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del metodo de pago no es valido.");
		}
	}

	public MethodPay findByName(String name) {
		if (PastleyValidate.isChain(name)) {
			MethodPay method = methodPayRepository.findByName(name);
			if (method != null) {
				return method;
			} else {
				throw new PastleyException(HttpStatus.NOT_FOUND,
						"No se ha encontrado ningun metodo de pago con el nombre " + name + ".");
			}
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND, "El nombre del metodo de pago no es valido.");
		}
	}

	@Override
	public List<MethodPay> findAll() {
		return methodPayRepository.findAll();
	}
	
	@Override
	public List<MethodPay> findByStatuAll(boolean statu) {
		return methodPayRepository.findByStatu(statu);
	}

	public List<MethodPay> findByRangeDateRegister(String start, String end) {
		if (PastleyValidate.isChain(start) && PastleyValidate.isChain(end)) {
			PastleyDate date = new PastleyDate();
			try {
				String array_date[] = { date.formatToDateTime(date.convertToDate(start.replaceAll("-", "/")), null),
						date.formatToDateTime(date.convertToDate(end.replaceAll("-", "/")), null) };
				return methodPayRepository.findByRangeDateRegister(array_date[0], array_date[1]);
			} catch (ParseException e) {
				throw new PastleyException(HttpStatus.NOT_FOUND,
						"El formato permitido para las fechas es: 'Año-Mes-Dia'.");
			}
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha recibido la fecha inicio o la fecha fin.");
		}
	}

	private Long findByStatisticSalePrivate(Long id) {
		if (id > 0) {
			Long count = methodPayRepository.countByMethodPaySale(id);
			return count == null ? 0L : count;
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND, "El id del metodo de pago no es valido.");
		}
	}

	public StatisticModel<MethodPay> findByStatisticSale(Long id) {
		return new StatisticModel<>(findById(id), findByStatisticSalePrivate(id));
	}
	
	public List<StatisticModel<MethodPay>> findByStatisticSaleAll() {
		try {
			List<MethodPay> methods = methodPayRepository.findByStatu(true);
			List<StatisticModel<MethodPay>> list = new ArrayList<>();
			for (MethodPay mp : methods) {
				list.add(new StatisticModel<MethodPay>(mp, findByStatisticSalePrivate(mp.getId())));
			}
			return list;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	@Override
	public MethodPay save(MethodPay entity) {
		return null;
	}
	
	public MethodPay save(MethodPay entity, byte type) {
		if (entity != null) {
			String message = entity.validate(false);
			String messageType = (type == 1) ? "registrar"
					: ((type == 2) ? "actualizar" : ((type == 3) ? "actualizar estado" : "n/a"));
			if (message == null) {
				MethodPay method = null;
				if (entity.getId() != null && entity.getId() > 0) {
					method = saveToUpdate(entity, type);
				} else {
					method = saveToSave(entity, type);
				}
				method = methodPayRepository.save(method);
				if (method != null) {
					return method;
				} else {
					throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha " + messageType + " el metodo de pago.");
				}
			} else {
				throw new PastleyException(HttpStatus.NOT_FOUND,
						"No se ha " + type + " el metodo de pago, " + message + ".");
			}
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND, "No se ha recibido el metodo de pago.");
		}
	}

	private MethodPay saveToSave(MethodPay entity, byte type) {
		if(validateName(entity.getName())){
			PastleyDate date = new PastleyDate();
			entity.uppercase();
			entity.setId(0L);
			entity.setDateRegister(date.currentToDateTime(null));
			entity.setDateUpdate(null);
			entity.setStatu(true);
		}else {
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"Ya existe un metodo de pago con el nombre " + entity.getName() + ".");
		}
		return entity;
	}

	private MethodPay saveToUpdate(MethodPay entity, byte type) {
		MethodPay method = findById(entity.getId());
		if(method != null) {
			boolean isName = (!method.getName().equalsIgnoreCase(entity.getName())) ? validateName(entity.getName()) : true;
			if(isName) {
				PastleyDate date = new PastleyDate();
				entity.uppercase();
				entity.setDateRegister(method.getDateRegister());
				entity.setDateUpdate(date.currentToDateTime(null));
				entity.setStatu((type == 3) ? !entity.isStatu() : entity.isStatu());
			}else {
				throw new PastleyException(HttpStatus.NOT_FOUND,
						"Ya existe un metodo de pago con el nombre " + entity.getName() + ".");
			}
		}else {
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha encontrado ningun metodo de pago con el id " + entity.getId() + ".");
		}
		return entity;
	}
	
	private boolean validateName(String name) {
		MethodPay method = null;
		try {
			method = findByName(name);
		}catch (PastleyException e) {
		}
		return (method == null) ? true : false;
	}

	@Override
	public boolean delete(Long id) {
		findById(id);
		Long count = findByStatisticSalePrivate(id);
		if (count == 0L) {
			methodPayRepository.deleteById(id);
			try {
				if(findById(id) == null) {
					return true;
				}
			} catch (PastleyException e) {
				return true;
			}
		} else {
			throw new PastleyException(HttpStatus.NOT_FOUND,
					"No se ha eliminado el metodo de pago con el id " + id + ", tiene asociada " + count + " ventas.");
		}
		throw new PastleyException(HttpStatus.NOT_FOUND,
				"No se ha eliminado el metodo de pago con el id " + id + ".");
	}
}
