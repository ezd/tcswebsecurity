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
		System.out.println("========================"+user.userType);
		Role role=null;
		if(user!=null && user.userType.equalsIgnoreCase("SPOC")) {
			role = new Role(RoleEnum.SPOC);
			
		}else if(user!=null && user.userType.equalsIgnoreCase("BRM")) {
			role = new Role(RoleEnum.BRM);
		}else {
			role=new Role(RoleEnum.USER);
		}
		
		if (userRegistrationService.isUserNameExists(user.getUsername())) {
			model.addAttribute("msg", "Email address " + user.getUsername() + " already in use. Try other Email.");
		} else {
			Role exisitingRole=userRegistrationService.getRole(role.getName());
			if(exisitingRole==null) {
				Role savedRole=userRegistrationService.saveRole(role);
				user.setRole(savedRole);
			}else {
				user.setRole(exisitingRole);
			}
			User savedUser=userRegistrationService.saveUserInfo(user);
			if (null == savedUser) {
				model.addAttribute("msg", "Something goes wrong. Unable to save.");
			} else {
				model.addAttribute("msg", "success");
				Authentication authentication = new UsernamePasswordAuthenticationToken(user, null,
						user.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				return "redirect:/profile/view";
			}
		}
		model.addAttribute("user", user);

		return "register";
	}

	@RequestMapping(value = "/userprofile/{id}", method = RequestMethod.GET)
	public String getProfile(Model model,Principal principal,@PathVariable("id") Long sendId) {
		if(principal!=null){
			String username=principal.getName();
			User backendUser=userRegistrationService.getUserByEmail(username);
			if(sendId!=backendUser.getId()){
				return "redirect:/";
			}else{
				model.addAttribute("user", backendUser);
				return "userprofile";
			}
			
		}else{
			return "/";
		}		
	}

}
