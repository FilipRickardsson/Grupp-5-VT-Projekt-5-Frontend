package models;

import java.util.List;

/**
 * POJO class for Altenative
 */
public class Alternative {

    private int alternativeId;
    private String text;
    private boolean correct;

    private Question question;

    private List<QuizzUser> quizzUsers;

    public Alternative() {
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestions(Question question) {
        this.question = question;
    }

    public List<QuizzUser> getQuizzUsers() {
        return quizzUsers;
    }

    public void setQuizzUsers(List<QuizzUser> quizzUsers) {
        this.quizzUsers = quizzUsers;
    }

    @Override
    public String toString() {
        return "Alternative{" + "alternativeId=" + alternativeId + ", text=" + text + ", correct=" + correct + ", questions=" + question + ", quizzUsers=" + quizzUsers + '}';
    }

}
