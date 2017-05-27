package models;

public class Quizz {

    private int quizzId;
    private String name;
    private boolean showResult;

    public Quizz() {
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
    
}
