package com.github.siberianintegrationsystems.restApp.data;

import com.github.siberianintegrationsystems.restApp.entity.SessionQuestion;
import com.github.siberianintegrationsystems.restApp.entity.SessionQuestionAnswer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SessionQuestionAnswerRepository
		extends CrudRepository<SessionQuestionAnswer, Long> {

	List<SessionQuestionAnswer> findAllBySessionQuestion(SessionQuestion sessionQuestion);
}