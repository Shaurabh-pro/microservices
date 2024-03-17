package com.microservices.quizservice.feigen;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.microservices.quizservice.model.QuestionWraper;
import com.microservices.quizservice.model.Response;



@FeignClient("QUESTION-SERVICE")
public interface FeigenQuizInterface {
	
		@GetMapping("question/ganerate")
	    public ResponseEntity<List<Integer>> getQuestionForQuiz(@RequestParam String categoryName, @RequestParam int numQ);
	    	
	    
	    @PostMapping("question/getQuestions")
	    public ResponseEntity<List<QuestionWraper>> getQuestionFromId(@RequestBody List<Integer> questionsId);
	    
	    
	    @PostMapping("question/getScore")
	    public ResponseEntity<List<Integer>> getScore(@RequestBody List<Response> responses);
	    

}
