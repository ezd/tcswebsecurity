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
import com.example.entities.Role;
import com.example.entities.StatusChange;
import com.example.entities.User;
import com.example.enums.RoleEnum;
import com.example.services.CandiateProfileService;
import com.example.services.CandidateProfileServiceImp;
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
		Role role=new Role();
		if(p!=null) {
		role=userRegistrationService.getRoleByEmail(p.getName());
		}else {
			role.setName("");
		}
		System.out.println("role is:"+role.getName());
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
		
		Role role=new Role();
		if(p!=null) {
		role=userRegistrationService.getRoleByEmail(p.getName());
		}else {
			role.setName("");
		}
		System.out.println("role is:"+role.getName());
		model.addAttribute("role", role);
		CandidateProfile  candidateProfile=candiateProfileService.getCandidateProfile(candidateId);
		model.addAttribute("candidate", candidateProfile);
		
		return CANDIDATE_REGISTER;
	}
	@RequestMapping(value="/profile/{candidateId}",method = RequestMethod.GET)
	public String viewCandidateProfile(Model model,Principal p,@PathVariable("candidateId") Long candidateId){

		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
		Role role=new Role();
		if(p!=null) {
		role=userRegistrationService.getRoleByEmail(p.getName());
		}else {
			role.setName("");
		}
		System.out.println("role is:"+role.getName());
		model.addAttribute("role", role);
		
		List<User> spocusers=userRegistrationService.getAllUsersByRole(RoleEnum.SPOC);
		model.addAttribute("spocusers", spocusers);
		System.out.println("spocsuers count is:"+spocusers.size());
		List<User> brmusers=userRegistrationService.getAllUsersByRole(RoleEnum.BRM);
		model.addAttribute("brmusers", brmusers);
		System.out.println("brmssuer count is:"+brmusers.size());
		
		CandidateProfile  candidateProfile=candiateProfileService.getCandidateProfile(candidateId);
		candidateProfile.setOldStatus(candidateProfile.getStatus());
		model.addAttribute("candidate", candidateProfile);
		
		System.out.println("***************************history fetching");
		List<StatusChange> history=candiateProfileService.getStatusChanges(candidateProfile);
		System.out.println("***************************"+history.size()+" history found"+history.isEmpty());
		model.addAttribute("history", history.isEmpty()?null:history);
		
		if(role.getName().equalsIgnoreCase("BRM")) {
			candidateProfile.setViewedByBrm(true);
			candiateProfileService.saveCandidate(candidateProfile);
		}else if(role.getName().equalsIgnoreCase("SPOC")) {
			candidateProfile.setViewedBySpoc(true);
			candiateProfileService.saveCandidate(candidateProfile);
		}
		
		return CANDIDATE_REGISTER;
	}

	@RequestMapping(value="/profile/add",method = RequestMethod.GET)
	public String addCandidateProfile(Model model,Principal p){
		Role role=new Role();
		if(p!=null) {
		role=userRegistrationService.getRoleByEmail(p.getName());
		}else {
			role.setName("");
		}
		System.out.println("role is:"+role.getName());
		model.addAttribute("role", role);
//		Role role=userRegistrationService.getRoleByEmail(p.getName());
		List<User> spocusers=userRegistrationService.getAllUsersByRole(RoleEnum.SPOC);
		model.addAttribute("spocusers", spocusers);
		System.out.println("spocsuers count is:"+spocusers.size());
		List<User> brmusers=userRegistrationService.getAllUsersByRole(RoleEnum.BRM);
		model.addAttribute("brmusers", brmusers);
		System.out.println("brmssuer count is:"+brmusers.size());
//		model.addAttribute("role", role);
		
		model.addAttribute("candidate", new CandidateProfile());
		return CANDIDATE_REGISTER;
	}
	
	@RequestMapping(value="/profile/add",method = RequestMethod.POST)
	public String addCandidateProfilePost(Model model,Principal p,CandidateProfile candidateProfile){

		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
		System.out.println("The status is changed in save: to "+candidateProfile.getStatus()+" from "+ candidateProfile.getOldStatus());
		Role role=new Role();
		
		if(p!=null) {
		role=userRegistrationService.getRoleByEmail(p.getName());
		}else {
			role.setName("");
		}
		System.out.println("role is:"+role.getName());
		model.addAttribute("role", role);
		candidateProfile.setDate(sdf.format(new Date()));
		CandidateProfile savedProfile=candiateProfileService.saveCandidate(candidateProfile);
		if(!candidateProfile.getOldStatus().equalsIgnoreCase(candidateProfile.getStatus())) {
			StatusChange statusChange=new StatusChange();
			statusChange.setChangedBy(p.getName());
			statusChange.setDate(sdf.format(new Date()));
			statusChange.setChangedFrom(candidateProfile.getOldStatus());
			statusChange.setChangedTo(candidateProfile.getStatus());
			statusChange.setCandidateProfile(savedProfile);
			candiateProfileService.saveChangeHistory(statusChange);
			
//			candidateProfile.getStatusChanges().add(statusChange);
		}
		if (null == savedProfile) {
			model.addAttribute("msg", "Something goes wrong. Unable to save.");
		} else {
			return "redirect:/profile/view";
		}
		candidateProfile.setOldStatus(candidateProfile.getStatus());
		model.addAttribute("candidate", candidateProfile);
		return CANDIDATE_REGISTER;
	}
	@RequestMapping("/admin/info")
	public String getAdminAccountProfile(){
		return ADMIN_PROFILE;
	}
	
	
	
}
