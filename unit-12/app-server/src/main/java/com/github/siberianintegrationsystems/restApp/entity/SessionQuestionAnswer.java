package com.github.siberianintegrationsystems.restApp.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SessionQuestionAnswer extends BaseEntity {

	@JoinColumn(name = "session_question_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private SessionQuestion sessionQuestion;

	@JoinColumn(name = "selected_answer_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Answer selectedAnswer;

	public SessionQuestion getSessionQuestion() {
		return sessionQuestion;
	}

	public void setSessionQuestion(SessionQuestion sessionQuestion) {
		this.sessionQuestion = sessionQuestion;
	}

	public Answer getSelectedAnswer() {
		return selectedAnswer;
	}

	public void setSelectedAnswer(Answer selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}
}
