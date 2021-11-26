package com.pastley.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pastley.models.entity.BuyDetail;

@Repository
public interface BuyDetailRepository extends JpaRepository<BuyDetail, Long> {

}
