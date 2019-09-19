package com.github.siberianintegrationsystems.restApp.data;

import com.github.siberianintegrationsystems.restApp.entity.Session;
import com.github.siberianintegrationsystems.restApp.entity.SessionQuestion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SessionQuestionRepository
		extends CrudRepository<SessionQuestion, Long> {

	List<SessionQuestion> findAllBySession(Session session);

}
