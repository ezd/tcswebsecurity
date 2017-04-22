package com.example.repository;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entities.User;
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);
//	User findByFirstName(String firstName);
	
	@Modifying
	@Query("UPDATE User u SET u.password=:password WHERE u.id=:userId")
	void updateUserPassword(@Param("userId") Long userId, @Param("") String password);

	
}
