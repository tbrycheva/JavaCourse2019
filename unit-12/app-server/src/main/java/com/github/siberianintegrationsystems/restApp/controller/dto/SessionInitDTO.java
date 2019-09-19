package com.github.siberianintegrationsystems.restApp.controller.dto;

import java.util.List;

public class SessionInitDTO {
	public String id;
	public String name;
	public List<AnsweredQuestionDTO> questionsList;

	public SessionInitDTO() {
	}
}