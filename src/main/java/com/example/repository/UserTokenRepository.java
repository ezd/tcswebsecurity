package com.example.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entities.User;
import com.example.entities.UserToken;
@Repository
public interface UserTokenRepository extends CrudRepository<UserToken, Long>{

	UserToken findByToken(String token);

	@Query(value="delete FROM user_token where user_id=1",nativeQuery=true)
	void deleteTokenByUserId();
//	void deleteByUser(User u);

	
}
