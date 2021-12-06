package com.pastley.models.service;

import java.util.List;

import com.pastley.models.dto.StatisticDTO;
import com.pastley.models.entity.TypePQR;

/**
 * @project Pastley-Contact.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/serbuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public interface TypePQRService {

	TypePQR findById(Long id);

	TypePQR findByName(String name);

	List<TypePQR> findAll();

	List<TypePQR> findByStatuAll(boolean statu);

	List<TypePQR> findByRangeDateRegister(String start, String end);

	Long findByStatisticTypePrivate(Long id);

	StatisticDTO<TypePQR> findByStatisticType(Long id);

	List<StatisticDTO<TypePQR>> findByStatisticTypeAll();

	TypePQR save(TypePQR entity, int type);

	boolean delete(Long id);
}
