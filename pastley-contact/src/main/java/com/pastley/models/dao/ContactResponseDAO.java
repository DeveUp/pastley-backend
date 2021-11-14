package com.pastley.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pastley.models.entity.ContactResponse;

/**
 * @project Pastley-Contact.
 * @author Soleimy Daniela Gomez Baron.
 * @Github https://github.com/Soleimygomez.
 * @contributors soleimygomez, leynerjoseoa, SerBuitragp jhonatanbeltran.
 * @version 1.0.0.
 */
@Repository
public interface ContactResponseDAO extends JpaRepository<ContactResponse, Long>{

}