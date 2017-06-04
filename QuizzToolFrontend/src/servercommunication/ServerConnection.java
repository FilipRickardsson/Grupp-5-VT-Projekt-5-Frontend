package servercommunication;

import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import models.Course;
import models.Question;
import models.Quizz;
import models.QuizzResult;
import models.QuizzUser;

public class ServerConnection {

    private static ServerConnection serverConnection;
    private Client client;

    private ServerConnection() {
        client = ClientBuilder.newClient();
    }

    public static ServerConnection getServerConnection() {
        if (serverConnection == null) {
            serverConnection = new ServerConnection();
        }

        return serverConnection;
    }

    public QuizzUser getQuizzUser(String username, String password) {
        QuizzUser quizzUser = client.target("http://localhost:8080/QuizzToolBackend/webapi/")
                .path("quizz_users/" + username + "&" + password)
                .request(MediaType.APPLICATION_JSON)
                .get(QuizzUser.class);

        return quizzUser;
    }

    public List<QuizzResult> getQuizzResult(int quizzId) {
        List<QuizzResult> quizzResults = client.target("http://localhost:8080/QuizzToolBackend/webapi/")
                .path("quizzresults/" + quizzId)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<QuizzResult>>() {
                });

        return quizzResults;
    }

    public List<Question> getQuestions(int quizzId) {
        List<Question> questions = client.target("http://localhost:8080/QuizzToolBackend/webapi/")
                .path("questions/" + quizzId)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Question>>() {
                });

        return questions;
    }

    public QuizzResult submitAnswers(int userId, int quizzId, String answers) {
        QuizzResult quizzResult = client.target("http://localhost:8080/QuizzToolBackend/webapi/")
                .path("quizzresults/" + userId + "&" + quizzId + "&" + answers)
                .request(MediaType.APPLICATION_JSON)
                .get(QuizzResult.class);

        return quizzResult;
    }

    public List<QuizzResult> getUserQuizzResults(int userId) {
        List<QuizzResult> quizzResult = client.target("http://localhost:8080/QuizzToolBackend/webapi/")
                .path("quizzresults/userresults/" + userId)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<QuizzResult>>() {
                });

        return quizzResult;
    }

    public void addQuizz(Quizz quizzToAdd) {
        client.target("http://localhost:8080/QuizzToolBackend/webapi/")
                .path("quizzes/")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(quizzToAdd), Quizz.class);
    }

    public List<Course> getCourses() {
        List<Course> courses = client.target("http://localhost:8080/QuizzToolBackend/webapi/")
                .path("courses/")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Course>>() {
                });
        return courses;
    }

}
