package com.example.quizapp;

import java.util.List;

public class Question {

    private String questionText;
    private List<String>options;
    private int correctAnswer;
    private int userAnswer;
    private boolean isAnswered;

    Question(String questionText,List<String>options,int correctAnswer){
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.userAnswer = -1;
        this.isAnswered = false;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(int userAnswer) {
        this.userAnswer = userAnswer;
        this.isAnswered = true;
    }

    public boolean isAnswered() {
        return isAnswered;
    }
}
