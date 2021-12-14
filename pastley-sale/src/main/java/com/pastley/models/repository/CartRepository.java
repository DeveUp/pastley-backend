package com.pastley.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pastley.models.entity.Cart;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	
	@Query(nativeQuery = false, value = "SELECT c FROM Cart c WHERE c.idCustomer = :idCustomer AND c.statu = :statu AND c.idProduct = :idProduct")
	public Cart findByCustomerAndProductAndStatu(boolean statu, Long idCustomer, Long idProduct);

	public List<Cart> findByStatu(boolean statu);
	
	public List<Cart> findByIdCustomer(Long idCustomer);
	
	@Query(nativeQuery = false, value = "SELECT c FROM Cart c WHERE c.statu = :statu AND c.idProduct = :idProduct")
	public List<Cart> findByProductAndStatus(Long idProduct, boolean statu);
	
	@Query(nativeQuery = false, value = "SELECT c FROM Cart c WHERE c.idCustomer = :idCustomer AND c.idProduct = :idProduct")
	public List<Cart> findByCustomerAndProduct(Long idCustomer, Long idProduct);
	
	@Query(nativeQuery = false, value = "SELECT c FROM Cart c WHERE c.statu = :statu AND c.idCustomer = :idCustomer")
	public List<Cart> findByCustomerAndStatus(Long idCustomer, boolean statu);
	
	@Query(nativeQuery = false, value = "SELECT c FROM Cart c WHERE c.dateRegister BETWEEN :start AND :end ORDER BY c.dateRegister")
	public List<Cart> findByRangeDateRegister(@Param("start") String start, @Param("end") String end);
	
	@Query(nativeQuery = false, value = "SELECT c FROM Cart c WHERE c.idCustomer = :idCustomer AND c.dateRegister BETWEEN :start AND :end ORDER BY c.dateRegister")
	public List<Cart> findByRangeDateRegisterAndCustomer(@Param("idCustomer") Long idCustomer, @Param("start") String start, @Param("end") String end);
	
}
