package com.example.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entities.CandidateProfile;
import com.example.entities.CandidateProfileReprot;
import com.example.entities.User;
@Repository
public interface CandidateProfileRepository extends CrudRepository<CandidateProfile, Long> {

	List<CandidateProfile> findByBrm(String brm);
	List<CandidateProfile> findBySpoc(String spoc);
	@Query(value="SELECT * FROM candidate_profile " + 
			"where candidate_profile.brm=:brm && candidate_profile.date between date(:start) and date(:end);",nativeQuery=true)
	List<CandidateProfile> findByBrmDateRange(@Param("brm")String brm, @Param("start")String start, @Param("end")String end);
		
}
