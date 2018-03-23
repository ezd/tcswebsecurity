package com.example.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.CandidateProfile;
import com.example.entities.Criteria;
import com.example.entities.Role;
import com.example.entities.StatusChange;
import com.example.entities.User;
import com.example.enums.RoleEnum;
import com.example.services.CandiateProfileService;
import com.example.services.CandidateProfileServiceImp;
import com.example.services.UserRegistrationService;



@Controller
public class ReportController {

	private static final String REPORT = "report";
	@Autowired
	CandiateProfileService candiateProfileService;
	@Autowired
	UserRegistrationService userRegistrationService;

	
/*
	@RequestMapping(value="/report",method = RequestMethod.GET)
	public String viewReport(Model model,Principal p){
		Role role=new Role();
		if(p!=null) {
		role=userRegistrationService.getRoleByEmail(p.getName());
		}		
		model.addAttribute("role", role);
		model.addAttribute("criteria", new Criteria());
		return REPORT;
	}
	
	@RequestMapping(value="/report",method = RequestMethod.POST)
	public String addCandidateProfilePost(Model model,Principal p,Criteria criteria){

		Role role=new Role();
		
		if(p!=null) {
		role=userRegistrationService.getRoleByEmail(p.getName());
		}else {
			role.setName("");
		}
		model.addAttribute("role", role);
		
		List<CandidateProfile> candidatesList=null;
		if(role.getName().equalsIgnoreCase(RoleEnum.BRM.getRoleName())) {
//			candidatesList=candiateProfileService.getReportByBRM(p.getName());
			System.out.println("BRM user with"+candidatesList.size()+" candidates");
		}
		model.addAttribute("candidatesList", candidatesList);
		
		return REPORT;
	}
	*/
	
	
	
}
