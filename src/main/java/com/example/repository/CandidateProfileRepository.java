package com.example.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entities.CandidateProfile;
import com.example.entities.User;
@Repository
public interface CandidateProfileRepository extends CrudRepository<CandidateProfile, Long> {

	List<CandidateProfile> findByBrm(String brm);
	List<CandidateProfile> findBySpoc(String spoc);
	
}
