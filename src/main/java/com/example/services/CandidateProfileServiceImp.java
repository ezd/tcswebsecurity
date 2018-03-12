package com.example.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entities.CandidateProfile;
import com.example.entities.StatusChange;
import com.example.entities.User;
import com.example.entities.excel.CandidateDto;
import com.example.entities.excel.ProfilesToSave;
import com.example.repository.CandidateProfileRepository;
import com.example.repository.ChangestatusRepo;


@Service
public class CandidateProfileServiceImp implements CandiateProfileService {

	@Autowired
	CandidateProfileRepository candidateProfileRepository;
	@Autowired
	UserRegistrationService userRegistrationService;
	
	@Autowired
	ChangestatusRepo changeStatrusRepo;
	
	@Override
	public List<CandidateProfile> getAllProfiles() {
		String spocName="";
		String brmName="";
		User userByEmailBrm = null;
		User userByEmailSpoc = null;
		List<CandidateProfile> profiles= (List<CandidateProfile>)candidateProfileRepository.findAll();
		List<CandidateProfile> profilesFillted=new ArrayList<CandidateProfile>();
		CandidateProfile cp=null;
		for(int i=0;i<profiles.size();i++) {
			spocName="Not Found";
			brmName="Not Found";
			cp=null;
			cp=profiles.get(i);
			cp.setViewed(true);
			
			userByEmailBrm = userRegistrationService.getUserByEmail(cp.getBrm());
			if(userByEmailBrm!=null) {
				brmName=userByEmailBrm.getLastName()+" "+userByEmailBrm.getFirstName();
			}
			userByEmailSpoc = userRegistrationService.getUserByEmail(cp.getSpoc());
			if(userByEmailSpoc!=null) {
				spocName=userByEmailSpoc.getLastName()+" "+userByEmailSpoc.getFirstName();
			}
			cp.setBrmFullName(brmName);
			cp.setSpocFullName(spocName);
			profilesFillted.add(cp);
			System.out.println("**************************"+cp.toString());
		}
		return profilesFillted;
	}

	@Override
	public CandidateProfile saveCandidate(CandidateProfile candidateProfile) {
		return candidateProfileRepository.save(candidateProfile);
	}

	@Override
	public void deleteCandidateProfile(Long id) {
		candidateProfileRepository.delete(id);
	}

	@Override
	public CandidateProfile getCandidateProfile(Long id) {
		return candidateProfileRepository.findOne(id);
	}

	@Override
	@Transactional
	public void saveAllCandidate(List<CandidateDto> candidatesToSave) {
		CandidateProfile candidate=null;
		for(CandidateDto cdt:candidatesToSave) {
			candidate=new CandidateProfile();
			candidate.setVendor(cdt.getVendor());
			candidate.setSkills(cdt.getSkills());
			candidate.setAnySpecialSkill(cdt.getSpecialSkills());
			candidate.setCandidateName(cdt.getCandidate_name());
			candidate.setDate(cdt.getDate());
			candidate.setLocation(cdt.getLocation());
			candidate.setTeam(cdt.getTeam());
			candidate.setBrm(cdt.getBRM());
			candidate.setSpoc(cdt.getSPOC());
			candidate.setStatus("New initiated");
			candidateProfileRepository.save(candidate);
			System.out.println(candidate.getVendor()+" saved");
		}
		
	}

	@Override
	public List<CandidateProfile> getProfilesByBRM(String name) {
		String spocName="";
		String brmName="";
		User userByEmailBrm = null;
		User userByEmailSpoc = null;
		List<CandidateProfile> profiles= candidateProfileRepository.findByBrm(name);
		List<CandidateProfile> profilesFillted=new ArrayList<CandidateProfile>();
		CandidateProfile cp=null;
		for(int i=0;i<profiles.size();i++) {
			spocName="Not Found";
			brmName="Not Found";
			cp=profiles.get(i);
			if(cp.isViewedByBrm()) {
				cp.setViewed(true);
			}else {
				cp.setViewed(false);
			}
			userByEmailBrm = userRegistrationService.getUserByEmail(cp.getBrm());
			if(userByEmailBrm!=null) {
				brmName=userByEmailBrm.getLastName()+" "+userByEmailBrm.getFirstName();
			}
			userByEmailSpoc = userRegistrationService.getUserByEmail(cp.getSpoc());
			if(userByEmailSpoc!=null) {
				spocName=userByEmailSpoc.getLastName()+" "+userByEmailSpoc.getFirstName();
			}
			cp.setBrmFullName(brmName);
			cp.setSpocFullName(spocName);
			profilesFillted.add(cp);
		}
		return profilesFillted;
	}

	@Override
	public List<CandidateProfile> getProfilesBySPOC(String name) {
		String spocName="";
		String brmName="";
		User userByEmailBrm = null;
		User userByEmailSpoc = null;
		List<CandidateProfile> profiles=candidateProfileRepository.findBySpoc(name);
		List<CandidateProfile> profilesFillted=new ArrayList<CandidateProfile>();
		CandidateProfile cp=null;
		for(int i=0;i<profiles.size();i++) {
			spocName="Not Found";
			brmName="Not Found";
			cp=profiles.get(i);
			if(cp.isViewedBySpoc()) {
				cp.setViewed(true);
			}else {
				cp.setViewed(false);
			}
			
			userByEmailBrm = userRegistrationService.getUserByEmail(cp.getBrm());
			if(userByEmailBrm!=null) {
				brmName=userByEmailBrm.getLastName()+" "+userByEmailBrm.getFirstName();
			}
			userByEmailSpoc = userRegistrationService.getUserByEmail(cp.getSpoc());
			if(userByEmailSpoc!=null) {
				spocName=userByEmailSpoc.getLastName()+" "+userByEmailSpoc.getFirstName();
			}
			cp.setBrmFullName(brmName);
			cp.setSpocFullName(spocName);
			profilesFillted.add(cp);
		}
		return profilesFillted;
		
	}

	@Override
	public StatusChange saveChangeHistory(StatusChange statusChange) {
		return changeStatrusRepo.save(statusChange);
		
	}

	@Override
	public List<StatusChange> getStatusChanges(CandidateProfile profile) {
		// TODO Auto-generated method stub
		return changeStatrusRepo.findByCandidateProfile(profile);
	}

}
