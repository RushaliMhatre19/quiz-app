package com.example.quizapp.controller;

import com.example.quizapp.entity.Question;
import com.example.quizapp.repository.QuestionRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping("/add-question")
    public String showAddQuestionForm(Model model) {

        model.addAttribute("question", new Question());

        return "add-question";
    }

    @PostMapping("/save-question")
    public String saveQuestion(Question question) {

        questionRepository.save(question);

        return "redirect:/add-question";
    }
    
    @GetMapping("/quiz")
    public String showQuiz(Model model) {

        List<Question> questions = questionRepository.findAll();

        model.addAttribute("questions", questions);

        return "quiz";
    }
    
    @PostMapping("/submit-quiz")
    public String submitQuiz(@RequestParam Map<String, String> answers,
                             Model model) {

        List<Question> questions = questionRepository.findAll();

        int score = 0;

        for (Question question : questions) {

            String selectedAnswer =
                    answers.get("question_" + question.getId());

            if (selectedAnswer != null &&
                selectedAnswer.equals(question.getCorrectAnswer())) {

                score++;
            }
        }

        model.addAttribute("score", score);
        model.addAttribute("total", questions.size());

        return "result";
    }
    
    @GetMapping("/")
    public String home() {
        return "home";
    }
    
    @GetMapping("/questions")
    public String viewQuestions(Model model) {

        List<Question> questions = questionRepository.findAll();

        model.addAttribute("questions", questions);

        return "questions";
    }
    
    @GetMapping("/delete-question/{id}")
    public String deleteQuestion(@PathVariable Long id) {

        questionRepository.deleteById(id);

        return "redirect:/questions";
    }
    
    @GetMapping("/edit-question/{id}")
    public String editQuestion(@PathVariable Long id, Model model) {

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question ID: " + id));

        model.addAttribute("question", question);

        return "edit-question";
    }
    
    @PostMapping("/update-question")
    public String updateQuestion(Question question) {

        questionRepository.save(question);

        return "redirect:/questions";
    }
    
}