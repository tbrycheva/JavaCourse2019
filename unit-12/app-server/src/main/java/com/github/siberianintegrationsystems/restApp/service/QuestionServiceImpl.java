package com.github.siberianintegrationsystems.restApp.service;

import com.github.siberianintegrationsystems.restApp.controller.dto.AnswerItemDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.QuestionsItemDTO;
import com.github.siberianintegrationsystems.restApp.data.AnswerRepository;
import com.github.siberianintegrationsystems.restApp.data.QuestionRepository;
import com.github.siberianintegrationsystems.restApp.entity.Answer;
import com.github.siberianintegrationsystems.restApp.entity.Question;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

	private final QuestionRepository questionRepository;
	private final AnswerRepository answerRepository;

	public QuestionServiceImpl(QuestionRepository questionRepository,
							   AnswerRepository answerRepository) {
		this.questionRepository = questionRepository;
		this.answerRepository = answerRepository;
	}

	@Override
	public QuestionsItemDTO createQuestion(QuestionsItemDTO dto) {
		Question question = new Question();
		question.setName(dto.name);
		questionRepository.save(question);
		dto.answers.forEach(answerItemDTO -> createAnswer(question, answerItemDTO));

		return new QuestionsItemDTO(question,
				answerRepository.findByQuestion(question));
	}

	@Override
	public QuestionsItemDTO editQuestion(QuestionsItemDTO dto) {
		Question question = questionRepository.getById(Long.valueOf(dto.id));
		question.setName(dto.name);
		questionRepository.save(question);
		answerRepository.deleteAllByQuestion(question);
		dto.answers.forEach(answerItemDTO -> createAnswer(question, answerItemDTO));
		return new QuestionsItemDTO(question,
				answerRepository.findByQuestion(question));
	}

	private Answer createAnswer(Question question, AnswerItemDTO dto) {
		Answer answer = new Answer();
		answer.setName(dto.answerText);
		answer.setCorrect(dto.isCorrect);
		answer.setQuestion(question);
		return answerRepository.save(answer);
	}

}

