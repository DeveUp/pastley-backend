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
	
	@Query(nativeQuery = true, value = "SELECT u.* FROM user AS u INNER JOIN user_role AS ur ON(u.id = ur.id_user AND ur.id_role = :idRole) WHERE u.id = :id")
	User findByIdAndIdRol(Long id, Long idRole);
	
    User findByNickname(String nickname);
    
    @Query(nativeQuery= true, value ="INSERT INTO user_role(id_user, id_role) values (:id, :idRole)")
    User createUserRole(Long id, Long idRole); 
    
    List<User> findByStatu(boolean statu);
    
	@Query(nativeQuery = true, value = "SELECT u.* FROM user AS u INNER JOIN user_role AS ur ON(u.id = ur.id_user AND ur.id_role = :idRole)")
	List<User> findByIdRole(Long idRole);	
}