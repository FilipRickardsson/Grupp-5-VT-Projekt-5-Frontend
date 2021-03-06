package models;

import java.util.List;

/**
 * POJO class for Question
 */
public class Question {

    private int id;
    private String text;

    private Quizz quizz;

    private List<Alternative> alternatives;

    public Question() {
    }

    public Question(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Alternative> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<Alternative> alternatives) {
        this.alternatives = alternatives;
    }

    public Quizz getQuizz() {
        return quizz;
    }

    public void setQuizz(Quizz quizz) {
        this.quizz = quizz;
    }

    @Override
    public String toString() {
        return "Question{" + "id=" + id + ", text=" + text + ", alternatives=" + alternatives + '}';
    }

}
