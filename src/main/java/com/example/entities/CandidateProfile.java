package com.example.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class CandidateProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	String vendor;
	String candidateName;
	String skills;
	String brm;
	String spoc;

	String team;
	String date;
	String status;

	public String getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

	public List<StatusChange> getStatusChanges() {
		return statusChanges;
	}

	public void setStatusChanges(List<StatusChange> statusChanges) {
		this.statusChanges = statusChanges;
	}

	@Transient
	String oldStatus;

	String location;
	String anySpecialSkill;
	String feedBack;

	@OneToMany(mappedBy = "candidateProfile", fetch = FetchType.EAGER)
	List<StatusChange> statusChanges=new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getTeam() {
		return team;
	}

	public String getBrm() {
		return brm;
	}

	public void setBrm(String brm) {
		this.brm = brm;
	}

	public String getSpoc() {
		return spoc;
	}

	public void setSpoc(String spoc) {
		this.spoc = spoc;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAnySpecialSkill() {
		return anySpecialSkill;
	}

	public void setAnySpecialSkill(String anySpecialSkill) {
		this.anySpecialSkill = anySpecialSkill;
	}

	public String getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}

	

}
