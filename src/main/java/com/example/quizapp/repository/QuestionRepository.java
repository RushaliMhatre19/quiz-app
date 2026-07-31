package com.example.quizapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.quizapp.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}