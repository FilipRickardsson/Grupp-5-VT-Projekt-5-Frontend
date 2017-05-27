package models;

import java.util.List;

public class Course {

    private int id;
    private String name;
    private List<Quizz> quizzes;

    public Course() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Quizz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quizz> quizzes) {
        this.quizzes = quizzes;
    }

    @Override
    public String toString() {
        return name;
    }

}
