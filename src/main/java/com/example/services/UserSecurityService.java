package com.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entities.Authority;
import com.example.entities.Role;
import com.example.entities.User;
import com.example.repository.UserRepository;

@Service
public class UserSecurityService implements UserDetailsService {

	@Autowired
	UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		User user=userRepo.findByUsername(userEmail);
		System.out.println("usernameis:::::::"+userEmail);
		if(user==null){
			throw new UsernameNotFoundException("User having "+userEmail+" not found");
		}
		System.out.println("user is ::::::::::"+user.getFirstName());
		for(Role r:user.getRoles()){
			System.out.println("roles"+r.getName());
		}
		System.out.println("********************************");
		for(GrantedAuthority a:user.getAuthorities()){
			System.out.println("gautorities"+a.getAuthority());
		}
		System.out.println("is the user enabled:"+user.isEnabled());
		return user;
	}

}
