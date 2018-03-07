package com.example.entities.excel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

public class ProfilesToSave {

@JsonView(Views.Public.class)
List<CandidateDto> profilesToSave;

public List<CandidateDto> getProfilesToSave() {
	return profilesToSave;
}

public void setProfilesToSave(List<CandidateDto> profilesToSave) {
	this.profilesToSave = profilesToSave;
}
	

}
