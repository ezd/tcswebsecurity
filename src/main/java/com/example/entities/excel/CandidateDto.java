package com.example.entities.excel;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CandidateDto {

@JsonProperty(value="vendor")
private String vendor;
@JsonProperty(value="candidate_name")
private String candidate_name;
@JsonProperty(value="Skills")
private String Skills;
@JsonProperty(value="BRM")
private String BRM;
@JsonProperty(value="SPOC")
private String SPOC;
@JsonProperty(value="date")
private String date;
@JsonProperty(value="location")
private String location;
@JsonProperty(value="specialSkills")
private String specialSkills;
@JsonProperty(value="team")
private String team;

public String getTeam() {
	return team;
}
public void setTeam(String team) {
	this.team = team;
}
public String getVendor() {
	return vendor;
}
public void setVendor(String vendor) {
	this.vendor = vendor;
}
public String getCandidate_name() {
	return candidate_name;
}
public void setCandidate_name(String candidate_name) {
	this.candidate_name = candidate_name;
}
public String getSkills() {
	return Skills;
}
public void setSkills(String skills) {
	Skills = skills;
}
public String getBRM() {
	return BRM;
}
public void setBRM(String bRM) {
	BRM = bRM;
}
public String getSPOC() {
	return SPOC;
}
public void setSPOC(String sPOC) {
	SPOC = sPOC;
}

public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
public String getSpecialSkills() {
	return specialSkills;
}
public void setSpecialSkills(String specialSkills) {
	this.specialSkills = specialSkills;
}
}
