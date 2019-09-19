package com.github.siberianintegrationsystems.restApp;

import com.github.siberianintegrationsystems.restApp.controller.dto.QuestionsItemDTO;
import com.github.siberianintegrationsystems.restApp.data.JournalRepository;
import com.github.siberianintegrationsystems.restApp.entity.Journal;
import com.github.siberianintegrationsystems.restApp.service.JournalServiceImpl;
import com.github.siberianintegrationsystems.restApp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class RestAppApplication {

	private JournalRepository journalRepository;
	private QuestionService questionService;

	@Autowired
	public RestAppApplication(JournalRepository journalRepository, QuestionService questionService) {
		this.journalRepository = journalRepository;
		this.questionService = questionService;
	}

	public static void main(String[] args) {
		SpringApplication.run(RestAppApplication.class, args);
	}

	@PostConstruct
	private void initData() {
		Journal journal = new Journal();
		journal.setId(JournalServiceImpl.QUESTIONS_JOURNAL_ID);
		journal.setName("Вопросы");
		journal.setDefaultPageSize(15L);
		journalRepository.save(journal);

		Journal journalSession = new Journal();
		journalSession.setId(JournalServiceImpl.SESSIONS_JOURNAL_ID);
		journalSession.setName("Сессии");
		journalSession.setDefaultPageSize(2L);
		journalRepository.save(journalSession);


		this.initQuestions();
	}

	private void initQuestions() {
		this.questionService.createQuestion(
				new QuestionsItemDTO("Какой город изображен на современной российской купюре 1000 рублей? ",
						"Хабаровск ", "Москва ", "Ярославль ", "Архангельск "));

		this.questionService.createQuestion(
				new QuestionsItemDTO("Сможете точно назвать годы Первой мировой войны?",
						"1917-1918 гг", "1914-1918 гг", "1914-1920 гг", "1914-1917 гг"));

		this.questionService.createQuestion(
				new QuestionsItemDTO("Оперу \"Борис Годунов\" написал...",
						"Николай Римский-Корсаков", "Михаил Глинка ", "Модест Мусоргский", "Петр Чайковский "));

		this.questionService.createQuestion(
				new QuestionsItemDTO("Сколько человек живет на планете Земля?",
						"около 12 млрд", "около 7,5 млрд", "около 5,5 млрд "));

		this.questionService.createQuestion(
				new QuestionsItemDTO("Кто является автором психоанализа?",
						"Фридрих Ницше", "Иммануил Кант", "Зигмунд Фрейд", "Карл Густав Юнг"));

		this.questionService.createQuestion(
				new QuestionsItemDTO("Человек состоит из воды на...",
						"65-70% ", "95% ", "85-90%", "10-20%"));
	}


}
