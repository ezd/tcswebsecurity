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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.CandidateProfile;
import com.example.entities.CandidateProfileReprot;
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

	
	@RequestMapping(value="/report",method = RequestMethod.GET)
	public String showReport(Model model,Principal p){
		
		Role role=new Role();
		if(p!=null) {
		role=userRegistrationService.getRoleByEmail(p.getName());
		}
		else{
			role.setName("");
		}
		System.out.println("role is:"+role.getName());
		model.addAttribute("role", role);
		
		List<CandidateProfileReprot> reportList = candiateProfileService.getReport("brm@gmail.com");
		model.addAttribute("reportList", reportList);
		return REPORT;
	}
	
	@RequestMapping(value="/reportByDate")
	public String showReportByDate(Model model,Principal p,@RequestParam("start") String start,@RequestParam("end") String end) {
		
		Role role=new Role();
		if(p!=null) {
		role=userRegistrationService.getRoleByEmail(p.getName());
		}
		else{
			role.setName("");
		}
		System.out.println("role is:"+role.getName());
		model.addAttribute("role", role);
		
		List<CandidateProfileReprot> reportList = candiateProfileService.getReportByDate("brm@gmail.com",start,end);
		model.addAttribute("reportList", reportList);
		
		return REPORT;
		
	}
	
	
	
}
