package com.example.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
public class StatusChange {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	String date;
	String changedFrom;
	public String getChangedFrom() {
		return changedFrom;
	}
	public void setChangedFrom(String changedFrom) {
		this.changedFrom = changedFrom;
	}
	String changedTo;
	String changedBy;
	@ManyToOne()
	@JoinColumn(name="candidate_id")
	CandidateProfile candidateProfile;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getChangedTo() {
		return changedTo;
	}
	public void setChangedTo(String changedTo) {
		this.changedTo = changedTo;
	}
	public String getChangedBy() {
		return changedBy;
	}
	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}
	public CandidateProfile getCandidateProfile() {
		return candidateProfile;
	}
	public void setCandidateProfile(CandidateProfile candidateProfile) {
		this.candidateProfile = candidateProfile;
	}
	
}
