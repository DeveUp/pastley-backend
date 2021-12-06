package com.pastley.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pastley.models.entity.Category;

/**
 * @project Pastley-Product.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category findByName(String name);

	List<Category> findByStatu(boolean statu);

	@Query(nativeQuery = false, value = "SELECT c FROM Category c WHERE c.dateRegister BETWEEN :start AND :end ORDER BY c.dateRegister")
	List<Category> findByRangeDateRegister(@Param("start") String start, @Param("end") String end);
}
