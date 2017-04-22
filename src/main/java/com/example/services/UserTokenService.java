package com.example.services;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entities.User;
import com.example.entities.UserToken;
import com.example.repository.UserRepository;
import com.example.repository.UserTokenRepository;

@Service
@Transactional
public class UserTokenService {
	
	@Autowired
	UserTokenRepository userTokenRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Value("${token.expiration.inmin}")
	int expirationMiniut;
	
	
	public UserToken createToken(User user,int expiration_time_in_min){
		//+Calendar.getInstance().getTimeInMillis()
		String token=UUID.randomUUID().toString();
		UserToken ut=new UserToken(token, LocalDateTime.now(), user, expiration_time_in_min);
		System.out.println("delete starts");
		Query deleteNativeQuery = entityManager.createNativeQuery("delete FROM user_token where user_id=:userId");
		deleteNativeQuery.setParameter("userId", user.getId());
		deleteNativeQuery.executeUpdate();
		System.out.println("delete ends");
//		entityManager
		return userTokenRepository.save(ut);
	}

	@PersistenceContext
    protected EntityManager entityManager;
 
    public EntityManager getEntityManager() {
        return entityManager;
    }
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
	public boolean isTokenNotExist(Long id, String token) {
		TypedQuery<UserToken> query = this.entityManager.createQuery("select ut from UserToken ut where ut.user.id=?1 and ut.token=?2", UserToken.class);
		query.setParameter(1, id);
		query.setParameter(2, token);
//	    query.setFirstResult(page * pageSize);
//	    query.setMaxResults(pageSize);
		return query.getResultList().isEmpty();
	}
	public boolean isTokenExpired(Long id, String token) {
		TypedQuery<UserToken> query = this.entityManager.createQuery("select ut from UserToken ut where ut.user.id=?1 and ut.token=?2", UserToken.class);
		query.setParameter(1, id);
		query.setParameter(2, token);
		UserToken tokenFound = query.getResultList().get(0);
		System.out.println("accessed time:"+LocalDateTime.now());
		System.out.println("token expirationtime:"+tokenFound.getExpiration_time());
		boolean isExpired=LocalDateTime.now().isAfter(tokenFound.getExpiration_time());
		System.out.println("comparision:"+isExpired);
		return isExpired;
	}

}
