package com.pastley.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pastley.models.entity.User;

/**
 * @project Pastley-User.
 * @author Leyner Jose Ortega Arias.
 * @Github https://github.com/leynerjoseoa.
 * @contributors serbuitrago.
 * @version 1.0.0.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	
	@Query(nativeQuery = false, value = "SELECT u FROM User u WHERE u.person.document = :document")
	User findByDocumentPerson(Long document);	
	
	@Query(nativeQuery = false, value = "SELECT u FROM User u WHERE u.nickname = :nickname AND u.role.id = :idRole")
	User findByNicknameAndIdRol(String nickname, Long idRole);
	
    User findByNickname(String nickname);
    
    List<User> findByStatu(boolean statu);
    
	@Query(nativeQuery = false, value = "SELECT u FROM User u WHERE u.role.id = :idRole")
	List<User> findByIdRole(Long idRole);	
}