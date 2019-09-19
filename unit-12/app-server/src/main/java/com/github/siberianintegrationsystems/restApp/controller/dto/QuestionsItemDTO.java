package com.github.siberianintegrationsystems.restApp.controller.dto;

import com.github.siberianintegrationsystems.restApp.entity.Answer;
import com.github.siberianintegrationsystems.restApp.entity.Question;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionsItemDTO extends JournalItemDTO {
	public String name;
	public List<AnswerItemDTO> answers;

	public QuestionsItemDTO() {

	}

	public QuestionsItemDTO(String name, String correctAnswer, String... anotherAnswers) {
		List<AnswerItemDTO> answers = Arrays.stream(anotherAnswers)
				.map(answerTitle -> new AnswerItemDTO(answerTitle, false))
				.collect(Collectors.toList());

		answers.add(new AnswerItemDTO(correctAnswer, true));

		this.name = name;
		this.answers = answers;
	}

	public QuestionsItemDTO(Question question, List<Answer> answers) {
		this.id = question.getId().toString();
		this.name = question.getName();
		this.answers = answers.stream()
				.map(AnswerItemDTO::new)
				.collect(Collectors.toList());
	}
}
