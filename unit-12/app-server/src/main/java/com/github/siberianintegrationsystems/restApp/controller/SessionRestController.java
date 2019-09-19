package com.github.siberianintegrationsystems.restApp.controller;

import com.github.siberianintegrationsystems.restApp.controller.dto.QuestionsItemDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.SessionInitDTO;
import com.github.siberianintegrationsystems.restApp.service.SessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/session")
public class SessionRestController {

	private final SessionService sessionService;

	public SessionRestController(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	@GetMapping("questions-new")
	public List<QuestionsItemDTO> initSession() {
		return sessionService.initSession();
	}

	@PostMapping()
	public String createSession(@RequestBody SessionInitDTO dto) {
		return sessionService.createSession(dto);
	}

}