package com.github.siberianintegrationsystems.restApp.controller;

import com.github.siberianintegrationsystems.restApp.controller.dto.JournalEntityDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.JournalItemDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.JournalRequestDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.JournalResultDTO;
import com.github.siberianintegrationsystems.restApp.service.JournalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/journal")
public class JournalRestController {

	private final JournalService journalService;

	public JournalRestController(JournalService journalService) {
		this.journalService = journalService;
	}

	@GetMapping("{id}")
	public JournalEntityDTO getJournalEntity(@PathVariable String id) {
		return new JournalEntityDTO(journalService.getJournal(id));
	}

	@PutMapping("{id}/rows")
	public JournalResultDTO getRows(@PathVariable String id,
									@RequestBody JournalRequestDTO req) {
		List<? extends JournalItemDTO> collect =
				journalService.getJournalRows(id, req);

		return new JournalResultDTO(collect.size(), collect);
	}
}
