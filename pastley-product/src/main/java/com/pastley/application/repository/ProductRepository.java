package com.pastley.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pastley.domain.Product;

/**
 * @project Pastley-Product.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    public Product findByName(String name);
    
	public List<Product> findByStatu(boolean statu);
	
	public List<Product> findBySupplies(boolean supplies);
	
    @Query(nativeQuery = false, value = "SELECT p FROM Product p WHERE p.category.id = :idCategory")
    public List<Product> findByIdCategory(@Param("idCategory") Long idCategory);
	
	@Query(nativeQuery = false, value = "SELECT p FROM Product p WHERE p.discount > 0")
	public List<Product> findProductByPromotion();
	
	@Query(nativeQuery = false, value = "SELECT p FROM Product p WHERE p.dateRegister BETWEEN :start AND :end ORDER BY p.dateRegister")
	public List<Product> findByRangeDateRegister(@Param("start") String start, @Param("end") String end);
}
