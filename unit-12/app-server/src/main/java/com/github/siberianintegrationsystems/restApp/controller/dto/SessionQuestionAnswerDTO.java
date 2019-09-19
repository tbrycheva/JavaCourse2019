package com.github.siberianintegrationsystems.restApp.controller.dto;

public class SessionQuestionAnswerDTO {
	public String id;
	public Boolean isSelected;

	public SessionQuestionAnswerDTO() {
	}

	public SessionQuestionAnswerDTO(String id, Boolean isSelected) {
		this.id = id;
		this.isSelected = isSelected;
	}
}