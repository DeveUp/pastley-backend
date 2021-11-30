package com.pastley.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pastley.domain.BuyDetail;

@Repository
public interface BuyDetailRepository extends JpaRepository<BuyDetail, Long> {

	@Query(nativeQuery = false, value = "SELECT bd FROM BuyDetail bd WHERE bd.buy.id= :idBuy")
	public List<BuyDetail> findByBuy(@Param("idBuy") Long idBuy);
}
