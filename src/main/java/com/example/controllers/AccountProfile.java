package com.example.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.CandidateProfile;
import com.example.entities.Role;
import com.example.entities.User;
import com.example.enums.RoleEnum;
import com.example.services.CandiateProfileService;
import com.example.services.UserRegistrationService;

@Controller
public class AccountProfile {

	private static final String ADMIN_PROFILE = "adminPrivate";
	private static final String PROFILE_VIEW = "profile_view";
	private static final String CANDIDATE_REGISTER = "candidate_register";
//	candidate_register
	@Autowired
	CandiateProfileService candiateProfileService;
	@Autowired
	UserRegistrationService userRegistrationService;
//	@RequestMapping("/")
//	public String getIndex(){
//		return PROFILE_HOME;
//	}
	
	
	@RequestMapping("/profile/view")
	public String getCandidateProfile(Model model,Principal p){
		

		Role role=userRegistrationService.getRoleByEmail(p.getName());
		model.addAttribute("role", role);
		
		List<CandidateProfile> candidatesList=null;
		if(role.getName().equalsIgnoreCase(RoleEnum.BRM.getRoleName())) {
			candidatesList=candiateProfileService.getProfilesByBRM(p.getName());
			System.out.println("BRM user with"+candidatesList.size()+" candidates");
		}else if(role.getName().equalsIgnoreCase(RoleEnum.SPOC.getRoleName())) {
			candidatesList=candiateProfileService.getProfilesBySPOC(p.getName());
			System.out.println("SPOC user with"+candidatesList.size()+" candidates");
		}else {
			candidatesList=candiateProfileService.getAllProfiles();
			System.out.println("any user with"+candidatesList.size()+" candidates");
		}
		model.addAttribute("candidatesList", candidatesList);
		
		return PROFILE_VIEW;
	}
	
	@RequestMapping(value="/profile/feedback/{candidateId}",method = RequestMethod.GET)
	public String updateViewCandidateProfile(Model model,@PathVariable("candidateId") Long candidateId,Principal p){
		
		Role role=userRegistrationService.getRoleByEmail(p.getName());
		model.addAttribute("role", role);
		CandidateProfile  candidateProfile=candiateProfileService.getCandidateProfile(candidateId);
		model.addAttribute("candidate", candidateProfile);
		return CANDIDATE_REGISTER;
	}
	@RequestMapping(value="/profile/{candidateId}",method = RequestMethod.GET)
	public String viewCandidateProfile(Model model,Principal p,@PathVariable("candidateId") Long candidateId){
		Role role=userRegistrationService.getRoleByEmail(p.getName());
		model.addAttribute("role", role);
		
		List<User> spocusers=userRegistrationService.getAllUsersByRole(RoleEnum.SPOC);
		model.addAttribute("spocusers", spocusers);
		System.out.println("spocsuers count is:"+spocusers.size());
		List<User> brmusers=userRegistrationService.getAllUsersByRole(RoleEnum.BRM);
		model.addAttribute("brmusers", brmusers);
		System.out.println("brmssuer count is:"+brmusers.size());
		
		CandidateProfile  candidateProfile=candiateProfileService.getCandidateProfile(candidateId);
		model.addAttribute("candidate", candidateProfile);
		return CANDIDATE_REGISTER;
	}

	@RequestMapping(value="/profile/add",method = RequestMethod.GET)
	public String addCandidateProfile(Model model,Principal p){
		Role role=userRegistrationService.getRoleByEmail(p.getName());
		List<User> spocusers=userRegistrationService.getAllUsersByRole(RoleEnum.SPOC);
		model.addAttribute("spocusers", spocusers);
		System.out.println("spocsuers count is:"+spocusers.size());
		List<User> brmusers=userRegistrationService.getAllUsersByRole(RoleEnum.BRM);
		model.addAttribute("brmusers", brmusers);
		System.out.println("brmssuer count is:"+brmusers.size());
		model.addAttribute("role", role);
		model.addAttribute("candidate", new CandidateProfile());
		return CANDIDATE_REGISTER;
	}
	
	@RequestMapping(value="/profile/add",method = RequestMethod.POST)
	public String addCandidateProfilePost(Model model,Principal p,CandidateProfile candidateProfile){
		Role role=userRegistrationService.getRoleByEmail(p.getName());
		model.addAttribute("role", role);
		CandidateProfile savedProfile=candiateProfileService.saveCandidate(candidateProfile);
		if (null == savedProfile) {
			model.addAttribute("msg", "Something goes wrong. Unable to save.");
		} else {
			return "redirect:/profile/view";
		}
		model.addAttribute("candidate", candidateProfile);
		return CANDIDATE_REGISTER;
	}
	@RequestMapping("/admin/info")
	public String getAdminAccountProfile(){
		return ADMIN_PROFILE;
	}
	
	
	
}
