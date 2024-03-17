package com.microservices.quizservice.controller;

import lombok.Data;

@Data
public class QuizDto {
	String category;
	int numQ;
	String title;
}
