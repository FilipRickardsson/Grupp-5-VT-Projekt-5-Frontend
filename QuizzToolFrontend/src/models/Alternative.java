package models;

import java.util.List;

public class Alternative {

    private int alternativeId;
    private String text;
    private boolean correct;

    private List<Question> questions;

    private List<QuizzUser> quizzUsers;

    public Alternative() {
    }

    public Alternative(int alternativeId, String text, boolean correct, List<Question> questions, List<QuizzUser> quizzUsers) {
        this.alternativeId = alternativeId;
        this.text = text;
        this.correct = correct;
        this.questions = questions;
        this.quizzUsers = quizzUsers;
    }

    public int getAlternativeId() {
        return alternativeId;
    }

    public void setAlternativeId(int alternativeId) {
        this.alternativeId = alternativeId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<QuizzUser> getQuizzUsers() {
        return quizzUsers;
    }

    public void setQuizzUsers(List<QuizzUser> quizzUsers) {
        this.quizzUsers = quizzUsers;
    }

    @Override
    public String toString() {
        return "Alternative{" + "alternativeId=" + alternativeId + ", text=" + text + ", correct=" + correct + ", questions=" + questions + ", quizzUsers=" + quizzUsers + '}';
    }

}
