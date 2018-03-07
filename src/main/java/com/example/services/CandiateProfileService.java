package com.example.services;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.entities.CandidateProfile;
import com.example.entities.Role;
import com.example.entities.User;
import com.example.entities.excel.CandidateDto;
import com.example.entities.excel.ProfilesToSave;

@Service
public interface CandiateProfileService {
	List<CandidateProfile> getAllProfiles();
	CandidateProfile getCandidateProfile(Long id);
	CandidateProfile saveCandidate(CandidateProfile candidateProfile);
	void deleteCandidateProfile(Long id);
	void saveAllCandidate(List<CandidateDto> candidatesToSave);
	List<CandidateProfile> getProfilesByBRM(String name);
	List<CandidateProfile> getProfilesBySPOC(String name);
	
}