package com.example.services;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.entities.Role;
import com.example.entities.User;
import com.example.enums.RoleEnum;

@Service
public interface UserRegistrationService {
User saveUserInfo(User userinfo);
User getUserInfo(Long id);
User updateUserInfo(User userinfo);
void deletUserInfo(Long id);
//boolean isValidUser(String email, String password);
User getUserByEmail(String userEmail);
boolean isValidUser(Long id);
boolean updateUserPassword(Long userId, String newPassword);
boolean isUserNameExists(String username);
boolean isRoleSaved(Role role);
Role saveRole(Role role);
Role getRole(String name);
Role getRoleByEmail(String name);
List<User> getAllUsers();
List<User> getAllUsersByRole(RoleEnum spoc);

}
