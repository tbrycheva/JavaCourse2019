package com.github.siberianintegrationsystems.restApp.controller.dto;

import com.github.siberianintegrationsystems.restApp.entity.Session;

import java.time.LocalDateTime;


public class SessionItemDTO extends JournalItemDTO {
	public String name;
	public LocalDateTime insertDate;
	public String result;

	public SessionItemDTO() {
	}

	public SessionItemDTO(Session session) {
		id = String.valueOf(session.getId());
		name = session.getName();
		result = String.valueOf(session.getResult());
		insertDate = session.getInsertDate();
	}

}