package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entities.CandidateProfile;
import com.example.entities.excel.CandidateDto;
import com.example.entities.excel.ProfilesToSave;
import com.example.repository.CandidateProfileRepository;


@Service
public class CandidateProfileServiceImp implements CandiateProfileService {

	@Autowired
	CandidateProfileRepository candidateProfileRepository;
	
	@Override
	public List<CandidateProfile> getAllProfiles() {
		return (List<CandidateProfile>)candidateProfileRepository.findAll();
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
			candidateProfileRepository.save(candidate);
			System.out.println(candidate.getVendor()+" saved");
		}
		
	}

	@Override
	public List<CandidateProfile> getProfilesByBRM(String name) {
		
		return candidateProfileRepository.findByBrm(name);
	}

	@Override
	public List<CandidateProfile> getProfilesBySPOC(String name) {
		return candidateProfileRepository.findBySpoc(name);
	}

}
