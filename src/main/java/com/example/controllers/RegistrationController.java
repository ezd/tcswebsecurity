package com.example.controllers;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.entities.Role;
import com.example.entities.User;
import com.example.enums.RoleEnum;
import com.example.services.UserRegistrationService;

@Controller
public class RegistrationController {
	@Autowired
	UserRegistrationService userRegistrationService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String getRegister(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String postRegister(Model model, User user) {
		System.out.println(user.toString());
		System.out.println("========================");

		Role userRole = new Role(RoleEnum.USER);
		Set<Role> roles = new HashSet<>();
		roles.add(userRole);
		if (userRegistrationService.isUserNameExists(user.getUsername())) {
			model.addAttribute("msg", "Email address " + user.getUsername() + " already in use. Try other Email.");
		} else {
			User savedUser=userRegistrationService.saveUserInfo(user, roles);
			if (null == savedUser) {
				model.addAttribute("msg", "Something goes wrong. Unable to save.");
			} else {
				model.addAttribute("msg", "success");
				Authentication authentication = new UsernamePasswordAuthenticationToken(user, null,
						user.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				return "redirect:/profile/" + savedUser.getId();
			}
		}
		model.addAttribute("user", user);

		return "register";
	}

	@RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
	public String getProfile(Model model,Principal principal,@PathVariable("id") Long sendId) {
		if(principal!=null){
			String username=principal.getName();
			User backendUser=userRegistrationService.getUserByEmail(username);
			if(sendId!=backendUser.getId()){
				return "redirect:/limited/info";
			}else{
				model.addAttribute("user", backendUser);
				return "profile";
			}
			
		}else{
			return "/";
		}		
	}

}
