package com.github.siberianintegrationsystems.restApp.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SessionQuestion extends BaseEntity {

	@JoinColumn(name = "question_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Question question;

	@JoinColumn(name = "session_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Session session;

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
}
