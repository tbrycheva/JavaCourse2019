package com.github.siberianintegrationsystems.restApp.service;


import com.github.siberianintegrationsystems.restApp.controller.dto.QuestionsItemDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.SessionInitDTO;

import java.util.List;

public interface SessionService {
	String createSession(SessionInitDTO dto);

	List<QuestionsItemDTO> initSession();
}