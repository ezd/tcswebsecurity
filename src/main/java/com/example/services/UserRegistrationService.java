package com.example.services;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.entities.Role;
import com.example.entities.User;

@Service
public interface UserRegistrationService {
User saveUserInfo(User userinfo,Set<Role> roles);
User getUserInfo(Long id);
User updateUserInfo(User userinfo);
void deletUserInfo(Long id);
//boolean isValidUser(String email, String password);
User getUserByEmail(String userEmail);
boolean isValidUser(Long id);
boolean updateUserPassword(Long userId, String newPassword);
boolean isUserNameExists(String username);

}
