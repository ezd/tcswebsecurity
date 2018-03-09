package com.example.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.CandidateProfile;
import com.example.entities.Role;
import com.example.entities.StatusChange;
@Repository
public interface ChangestatusRepo extends CrudRepository<StatusChange, Long> {


	List<StatusChange> findByCandidateProfile(CandidateProfile profile);


}
