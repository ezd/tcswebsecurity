package com.example.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entities.Role;
import com.example.entities.User;
import com.example.repository.RoleRepo;
import com.example.repository.UserRepository;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

	@Autowired
	UserRepository userInfoRepository;
	
	@Autowired
	RoleRepo roleRepository;
	
	@Override
	@Transactional
	public User saveUserInfo(User userinfo,Set<Role> roles) {
		String pw_hash = BCrypt.hashpw(userinfo.getPassword(), BCrypt.gensalt());
		userinfo.setPassword(pw_hash);
		for(Role role:roles){
			roleRepository.save(role);
		}
		userinfo.getRoles().addAll(roles);
		return userInfoRepository.save(userinfo);
	}

	@Override
	public User getUserInfo(Long id) {
		return userInfoRepository.findOne(id);
	}

	@Override
	public User updateUserInfo(User userinfo) {
		return userInfoRepository.save(userinfo);
	}

	@Override
	public void deletUserInfo(Long id) {
		userInfoRepository.delete(id);

	}

	@Override
	public User getUserByEmail(String userEmail) {
		return userInfoRepository.findByUsername(userEmail);
	}

	@Override
	public boolean isValidUser(Long id) {
		return userInfoRepository.exists(id);
	}

	@Override
	public boolean updateUserPassword(Long userId, String newPassword) {
		System.out.println("userid in service is:"+userId);
		User exisitingUser=userInfoRepository.findOne(userId);
		String pw_hash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
		System.out.println("xxxxxxxxxxxxxxxx:"+pw_hash);
		exisitingUser.setPassword(pw_hash);
		User savedUser=userInfoRepository.save(exisitingUser);
		return savedUser!=null;
	}

	@Override
	public boolean isUserNameExists(String username) {
		User user=userInfoRepository.findByUsername(username);
		return (user!=null);
	}

//	@Override
//	public User getUserInfoByEmail(String email) {
//		return userInfoRepository.findByEmail(email);
//	}

//	@Override
//	public boolean isValidUser(String email, String candidate_password) {
//		User userinfo=this.getUserInfoByEmail(email);
//		return BCrypt.checkpw(candidate_password, userinfo.getPassword());
//	}

//	@Override
//	public User getUserInfoByEmail(String email) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	

}
