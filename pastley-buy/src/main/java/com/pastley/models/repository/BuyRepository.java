package com.pastley.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pastley.models.entity.Buy;

@Repository
public interface BuyRepository  extends JpaRepository<Buy, Long>{

}
