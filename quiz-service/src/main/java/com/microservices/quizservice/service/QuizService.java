package com.microservices.quizservice.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.microservices.quizservice.Dao.QuizDao;
import com.microservices.quizservice.feigen.FeigenQuizInterface;
import com.microservices.quizservice.model.QuestionWraper;
import com.microservices.quizservice.model.Quiz;
import com.microservices.quizservice.model.Response;

@Service
public class QuizService {

	@Autowired
	private QuizDao quizDao;
	
	@Autowired
	private FeigenQuizInterface feigenQuizInterface;

	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

		List<Integer> question = feigenQuizInterface.getQuestionForQuiz(category, numQ).getBody();
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setQuestionsIds(question);
		quizDao.save(quiz);

		return new ResponseEntity<>("success", HttpStatus.CREATED);

	}

	public ResponseEntity<List<QuestionWraper>> getQuizQuestion(int id) {
		Quiz quiz = quizDao.findById(id).get();
		List<Integer> questionIds = quiz.getQuestionsIds();
		ResponseEntity<List<QuestionWraper>> questions = feigenQuizInterface.getQuestionFromId(questionIds);
		return questions; // Quiz with the given id not found
	}

	public ResponseEntity<List<Integer>> calculateResult(int id, List<Response> responses) {
	 ResponseEntity<List<Integer>> scoreIntegers = feigenQuizInterface.getScore(responses);
		return scoreIntegers;
	}

}
