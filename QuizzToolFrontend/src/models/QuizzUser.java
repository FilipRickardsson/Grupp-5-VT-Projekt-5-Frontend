package models;

import quizztoolfrontend.QuizzUserType;

public class QuizzUser {

    private int id;
    private String username;
    private String password;
    private QuizzUserType quizzUserType;

    public QuizzUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public QuizzUserType getQuizzUserType() {
        return quizzUserType;
    }

    public void setQuizzUserType(QuizzUserType quizzUserType) {
        this.quizzUserType = quizzUserType;
    }

}
