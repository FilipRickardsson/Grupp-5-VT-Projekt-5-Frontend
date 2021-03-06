package models;

/**
 * POJO class for QuizzResult
 */
public class QuizzResult {

    private int id;
    private GradeType grade;
    private int points;
    private String quizzTime;
    private QuizzUser quizzUser;
    private Quizz quizz;

    public QuizzResult() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GradeType getGrade() {
        return grade;
    }

    public void setGrade(GradeType grade) {
        this.grade = grade;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getTime() {
        return quizzTime;
    }

    public void setTime(String time) {
        this.quizzTime = time;
    }

    public QuizzUser getQuizzUser() {
        return quizzUser;
    }

    public void setQuizzUser(QuizzUser quizzUser) {
        this.quizzUser = quizzUser;
    }

    public Quizz getQuizz() {
        return quizz;
    }

    public void setQuizz(Quizz quizz) {
        this.quizz = quizz;
    }

}
