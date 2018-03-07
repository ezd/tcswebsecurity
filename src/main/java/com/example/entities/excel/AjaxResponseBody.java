package com.example.entities.excel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

public class AjaxResponseBody {
	@JsonView(Views.Public.class)
	String msg;
	@JsonView(Views.Public.class)
	String code;
	@JsonView(Views.Public.class)
	List<CandidateDto> candidatesToSave;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<CandidateDto> getCandidatesToSave() {
		return candidatesToSave;
	}
	public void setCandidatesToSave(List<CandidateDto> candidatesToSave) {
		this.candidatesToSave = candidatesToSave;
	}
	
	
	

}
