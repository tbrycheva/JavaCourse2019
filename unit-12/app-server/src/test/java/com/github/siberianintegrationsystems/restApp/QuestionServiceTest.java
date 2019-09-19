package com.github.siberianintegrationsystems.restApp;

import com.github.siberianintegrationsystems.restApp.data.AnswerRepository;
import com.github.siberianintegrationsystems.restApp.data.QuestionRepository;
import com.github.siberianintegrationsystems.restApp.service.QuestionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionServiceTest {

	private QuestionService questionService;
	private QuestionRepository questionRepository;
	private AnswerRepository answerRepository;

	@Test
	public void createQuestionTest() {


	}

}
