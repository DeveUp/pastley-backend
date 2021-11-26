package com.pastley.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pastley.models.entity.Provider;

@Repository
public interface ProviderRepository  extends JpaRepository<Provider, Long>{

}
