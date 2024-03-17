package com.microservices.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.microservices.Dao.QuestionDao;
import com.microservices.model.Question;
import com.microservices.model.QuestionWraper;
import com.microservices.model.Response;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            List<Question> questions = questionDao.findAll();
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (DataAccessException e) {
            e.printStackTrace(); // Log the error for debugging purposes
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Question>> getAllQuestionsByCategory(String category) {
        List<Question> questions = questionDao.findByCategory(category);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDao.save(question);
        return new ResponseEntity<>("Question added successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteQuestionById(int id) {
        questionDao.deleteById(id);
        return new ResponseEntity<>("Question deleted successfully", HttpStatus.OK);
    }

	public ResponseEntity<List<Integer>> getQuestionForQuiz(String categoryName, int numQ) {
		
		List<Integer> question = questionDao.findRandomQuestionsByCategory(categoryName, numQ);
		
		return new ResponseEntity<>(question, HttpStatus.OK);
	}
	
		
	public ResponseEntity<List<QuestionWraper>> getQuestionFromId(List<Integer> questionIds) {
		List<QuestionWraper> wrapers = new ArrayList<>();
		List<Question> questions = new ArrayList<>();
		for (Integer id : questionIds) {
			questions.add(questionDao.findById(id).get());
		}
		for (Question question : questions) {
			QuestionWraper questionWraper = new QuestionWraper();
			questionWraper.setId(question.getId());
			questionWraper.setQuestionTitle(question.getQuestionTitle());
			questionWraper.setOption1(question.getOption1());
			questionWraper.setOption2(question.getOption2());
			questionWraper.setOption3(question.getOption3());
			questionWraper.setOption4(question.getOption4());
			wrapers.add(questionWraper);
		}

		return new ResponseEntity<>(wrapers, HttpStatus.OK);
	}

	public ResponseEntity<List<Integer>> getScore(List<Response> responses) {
	    int right = 0;
	    List<Integer> scores = new ArrayList<>();
	    for(Response response : responses) {
	        Question question = questionDao.findById(response.getId()).orElse(null); // Handle if question not found
	        if(question != null && response.getResponses().equals(question.getRightAnswer()))
	            right++;
	    }
	    scores.add(right);
	    return new ResponseEntity<>(scores, HttpStatus.OK);
	    
	}
	
	
	
	
	
}
