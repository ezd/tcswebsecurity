package com.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AccountProfile {

	private static final String EMPLOYEE_PRIVATE_PROFILE = "empPrivate";
	private static final String EMPLOYER_PUBLIC_PROFILE = "emprPrivate";
	private static final String ADMIN_PROFILE = "adminPrivate";
	private static final String LIMITED_PROFILE = "limited";
//	@RequestMapping("/")
//	public String getIndex(){
//		return PROFILE_HOME;
//	}
	
	@RequestMapping("/emp/info")
	public String getEmployeeAccountProfile(){
		return EMPLOYEE_PRIVATE_PROFILE;
	}
	
	
	@RequestMapping("/empr/info")
	public String getEmployerAccountProfile(){
		return EMPLOYER_PUBLIC_PROFILE;
	}
	
	@RequestMapping("/limited/info")
	public String getPublicAccountProfile(){
		return LIMITED_PROFILE;
	}
	
	@RequestMapping("/admin/info")
	public String getAdminAccountProfile(){
		return ADMIN_PROFILE;
	}
	
	
	
}
