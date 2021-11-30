package com.pastley.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pastley.domain.Company;

/**
 * @project Pastley-Contact.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/serbuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
