package com.github.siberianintegrationsystems.restApp.controller.dto;

import java.util.List;

public class AnsweredQuestionDTO {
	public String id;
	public List<SessionQuestionAnswerDTO> answersList;

	public AnsweredQuestionDTO() {
	}

	public AnsweredQuestionDTO(String id, List<SessionQuestionAnswerDTO> answersList) {
		this.id = id;
		this.answersList = answersList;
	}
}
