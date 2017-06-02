package models;

import java.util.ArrayList;
import java.util.List;

public class Quizz {

    private int quizzId;
    private String name;
    private boolean showResult;
    private List<Question> questions;

    public Quizz() {
        this.questions=new ArrayList<Question>();
    }
  
    public int getQuizzId() {
        return quizzId;
    }

    public void setQuizzId(int quizzId) {
        this.quizzId = quizzId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShowResult() {
        return showResult;
    }

    public void setShowResult(boolean showResult) {
        this.showResult = showResult;
    }

    @Override
    public String toString() {
        return name;
    }
    
    public List<Question> getQuestions() {
        return questions;
    }
    
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    
}
