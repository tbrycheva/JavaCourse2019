package com.github.siberianintegrationsystems.restApp.service;

import com.github.siberianintegrationsystems.restApp.controller.dto.QuestionsItemDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.SessionInitDTO;
import com.github.siberianintegrationsystems.restApp.data.*;
import com.github.siberianintegrationsystems.restApp.entity.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class SessionServiceImpl implements SessionService {

	private final SessionRepository sessionRepository;
	private final SessionQuestionRepository sessionQuestionRepository;
	private final SessionQuestionAnswerRepository sessionQuestionAnswerRepository;
	private final QuestionRepository questionRepository;
	private final AnswerRepository answerRepository;

	public SessionServiceImpl(SessionQuestionRepository sessionQuestionRepository,
							  SessionRepository sessionRepository,
							  SessionQuestionAnswerRepository sessionQuestionAnswerRepository,
							  QuestionRepository questionRepository,
							  AnswerRepository answerRepository) {
		this.sessionQuestionRepository = sessionQuestionRepository;
		this.sessionRepository = sessionRepository;
		this.sessionQuestionAnswerRepository = sessionQuestionAnswerRepository;
		this.questionRepository = questionRepository;
		this.answerRepository = answerRepository;
	}


	@Override
	public String createSession(SessionInitDTO dto) {
		Session session = new Session();
		session.setName(dto.name);
		Clock clock = Clock.system(ZoneId.of("Europe/Moscow"));
		session.setInsertDate(LocalDateTime.now(clock));
		sessionRepository.save(session);

		Integer totalQuestions = dto.questionsList.size();
		BigDecimal correctQuestions = BigDecimal.valueOf(0);

		Double questionsPercent = dto.questionsList.stream().map(answerQuestionDTO -> {
			Question question = this.questionRepository.getById(Long.parseLong(answerQuestionDTO.id));

			SessionQuestion sessionQuestion = new SessionQuestion();
			sessionQuestion.setSession(session);
			sessionQuestion.setQuestion(question);

			this.sessionQuestionRepository.save(sessionQuestion);

			Integer totalAnswers = answerQuestionDTO.answersList.size();

			Integer totalCorrectAnswers = Math.toIntExact(this.answerRepository.findByQuestion(question).stream().filter(Answer::getCorrect).count());

			Integer selectedAnswers = Math.toIntExact(answerQuestionDTO.answersList.stream()
					.filter(sessionQuestionAnswerDTO -> sessionQuestionAnswerDTO.isSelected).count());

			Integer selectedCorrectAnswers = Math.toIntExact(answerQuestionDTO.answersList.stream()
					.filter(sessionQuestionAnswerDTO -> sessionQuestionAnswerDTO.isSelected)
					.map(sessionQuestionAnswerDTO -> {

						Answer answer = this.answerRepository.getById(Long.parseLong(sessionQuestionAnswerDTO.id));

						SessionQuestionAnswer sessionQuestionAnswer = new SessionQuestionAnswer();
						sessionQuestionAnswer.setSessionQuestion(sessionQuestion);
						sessionQuestionAnswer.setSelectedAnswer(answer);

						this.sessionQuestionAnswerRepository.save(sessionQuestionAnswer);

						return answer;
					})
					.filter(Answer::getCorrect)
					.count());

			     /* Передаем сколько всего правильных ответов у данного вопроса, выбранные пользователем ответы,
			     и сколько из них верные. Вычисляем процент верно отмеченных ответов для данного вопроса*/
			return countPercentCorrectAnswersForQuestion(totalCorrectAnswers, selectedAnswers, selectedCorrectAnswers);

		}).reduce(0D, Double::sum);

		/*Считаем процент верных ответов для общего количества вопросов*/
		session.setResult(this.countPercentCorrectAnswers(questionsPercent, totalQuestions));

		this.sessionRepository.save(session);

		return String.valueOf(session.getResult());
	}

	@Override
	public List<QuestionsItemDTO> initSession() {
		List<QuestionsItemDTO> randomListQuestionsDTO = this.getRandomListQuestionsDTO(questionRepository.findAll().stream()
				.map(question -> new QuestionsItemDTO(question,
						answerRepository.findByQuestion(question)))
				.collect(Collectors.toList()), 4);
		return randomListQuestionsDTO;
	}

	private List<QuestionsItemDTO> getRandomListQuestionsDTO(List<QuestionsItemDTO> questionsItemDTOS, int count) {
		Random rand = new Random();
		int randomIndex = rand.nextInt(questionsItemDTOS.size());
		List<QuestionsItemDTO> newList = new ArrayList<QuestionsItemDTO>();
		while (!newList.contains(questionsItemDTOS.get(randomIndex))) {
			newList.add(questionsItemDTOS.get(randomIndex));
		}
		while (newList.size() < count) {
			randomIndex = rand.nextInt(questionsItemDTOS.size());
			if (!newList.contains(questionsItemDTOS.get(randomIndex))) {
				newList.add(questionsItemDTOS.get(randomIndex));
			}
		}
		return newList;
	}


	private BigDecimal countPercentCorrectAnswers(Double questionsPercent, Integer totalQuestions) {
		BigDecimal result = BigDecimal.valueOf(questionsPercent * 100 / totalQuestions);
		return result = result.setScale(2, RoundingMode.HALF_UP);
	}

	private Double countPercentCorrectAnswersForQuestion(Integer totalCorrectAnswers, Integer selectedAnswers, Integer selectedCorrectAnswers) {
		Integer selectedNoCorrectAnswers = selectedAnswers - selectedCorrectAnswers;

		Double diff = 1D / totalCorrectAnswers;
		return Math.max((selectedCorrectAnswers * diff) - (selectedNoCorrectAnswers * diff), 0);
	}


}
