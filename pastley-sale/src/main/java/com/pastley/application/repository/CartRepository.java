package com.pastley.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pastley.domain.Cart;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	
	/**
	 * Method that allows consulting a product in the cart by the customer id and the product id.
	 * @param idCustomer, Represents the customer id.
	 * @param statu, Represents the status of the product.
	 * @param idProduct, Represents the product id.
	 * @return Cart.
	 */
	@Query(nativeQuery = false, value = "SELECT c FROM Cart c WHERE c.idCustomer = :idCustomer AND c.statu = :statu AND c.idProduct = :idProduct")
	public Cart findByCustomerAndProductAndStatu(boolean statu, Long idCustomer, Long idProduct);

	/**
	 * Method that allows you to consult all the carts by their status.
	 * @param statu, Represents the status of the product in cart.
	 * @return List of carts.
	 */
	public List<Cart> findByStatu(boolean statu);
	
	/**
	 * Method that allows consulting all the products of a client.
	 * @param idCustomer, Represents the customer id.
	 * @return List of carts.
	 */
	public List<Cart> findByIdCustomer(Long idCustomer);
	
	/**
	 * Method that allows you to check the products in the cart by product and status.
	 * @param idProduct, Represents the product id.
	 * @param statu, Represents the status of the product.
	 * @return List of carts.
	 */
	@Query(nativeQuery = false, value = "SELECT c FROM Cart c WHERE c.statu = :statu AND c.idProduct = :idProduct")
	public List<Cart> findByProductAndStatus(Long idProduct, boolean statu);
	
	/**
	 * Method that allows consulting a product in the cart by the customer id and the product id.
	 * @param idCustomer, Represents the customer id.
	 * @param idProduct, Represents the product id.
	 * @return List of carts.
	 */
	@Query(nativeQuery = false, value = "SELECT c FROM Cart c WHERE c.idCustomer = :idCustomer AND c.idProduct = :idProduct")
	public List<Cart> findByCustomerAndProduct(Long idCustomer, Long idProduct);
	
	/**
	 * Method that allows to consult all the products of a client and by their status.
	 * @param id, Represents the customer id.
	 * @param statu, Represents the status of the product.
	 * @return List of carts.
	 */
	@Query(nativeQuery = false, value = "SELECT c FROM Cart c WHERE c.statu = :statu AND c.idCustomer = :idCustomer")
	public List<Cart> findByCustomerAndStatus(Long idCustomer, boolean statu);
	
	/**
	 * Method that allows you to filter the products in the cart that are registered between a range of dates.
	 * @param start, Represents the start date.
	 * @param end, Represents the end date.
	 * @return List of carts.
	 */
	@Query(nativeQuery = false, value = "SELECT c FROM Cart c WHERE c.dateRegister BETWEEN :start AND :end ORDER BY c.dateRegister")
	public List<Cart> findByRangeDateRegister(@Param("start") String start, @Param("end") String end);
	
	/**
	 * Method that allows you to filter the products in the cart that are registered between a range of dates and the customer's id.
	 * @param idCustomer, Represents the customer id.
	 * @param start, Represents the start date.
	 * @param end, Represents the end date.
	 * @return List of carts.
	 */
	@Query(nativeQuery = false, value = "SELECT c FROM Cart c WHERE c.idCustomer = :idCustomer AND c.dateRegister BETWEEN :start AND :end ORDER BY c.dateRegister")
	public List<Cart> findByRangeDateRegisterAndCustomer(@Param("idCustomer") Long idCustomer, @Param("start") String start, @Param("end") String end);
	
}
