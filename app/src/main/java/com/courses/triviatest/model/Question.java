package com.courses.triviatest.model;

public class Question {
    private String answer;
    private boolean answerTrue;
    private boolean isAnswered = false;

    public Question() {

    }

    public Question(String answer, boolean answerTrue) {
        this.answer = answer;
        this.answerTrue = answerTrue;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isAnswerTrue() {
        return answerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        this.answerTrue = answerTrue;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        this.isAnswered = answered;
    }

    @Override
    public String toString() {
        return "\nQuestion { " +
                "answer='" + answer + '\'' +
                ", answerTrue=" + answerTrue +
                ", isAnswered=" + isAnswered +
                " }";
    }
}
