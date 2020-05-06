package com.clim8.model;

import java.util.List;


public class Questionnaire {
    String question;
    List<String> answers;

    public Questionnaire(String question, List<String> answers) {
        this.question = question;
        this.answers = answers;
    }

    public Questionnaire() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
