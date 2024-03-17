package com.microservices.quizservice.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservices.quizservice.model.Quiz;

public interface QuizDao extends JpaRepository<Quiz, Integer> {

}
