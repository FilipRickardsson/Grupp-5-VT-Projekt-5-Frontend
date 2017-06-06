package servercommunication;

import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import models.Alternative;
import models.Course;
import models.Question;
import models.Quizz;
import models.QuizzResult;
import models.QuizzUser;

/**
 * Singelton Handles the communication with the REST-server
 *
 */
public class ServerConnection {

    private static ServerConnection serverConnection;
    private Client client;

    /**
     * Constructor which creates a new client object
     */
    private ServerConnection() {
        client = ClientBuilder.newClient();
    }

    public static ServerConnection getServerConnection() {
        if (serverConnection == null) {
            serverConnection = new ServerConnection();
        }

        return serverConnection;
    }

    /**
     * Retrieves a quizzuser from the server to check credentials when logging
     * in
     *
     * @param username inputted by user
     * @param password inputted by user
     * @return QuizzUser if the credentials were correct
     */
    public QuizzUser getQuizzUser(String username, String password) {
        QuizzUser quizzUser = client.target("http://localhost:8080/QuizzToolBackend/webapi/")
                .path("quizz_users/" + username + "&" + password)
                .request(MediaType.APPLICATION_JSON)
                .get(QuizzUser.class);

        return quizzUser;
    }

    /**
     * Retrieves all quizz results for a specific quizz
     *
     * @param quizzId Id of the quizz which to retrieve results for
     * @return List of results
     */
    public List<QuizzResult> getQuizzResult(int quizzId) {
        List<QuizzResult> quizzResults = client.target("http://localhost:8080/QuizzToolBackend/webapi/")
                .path("quizzresults/" + quizzId)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<QuizzResult>>() {
                });

        return quizzResults;
    }

    /**
     * Retrieves all questions for a specific quizz
     *
     * @param quizzId Id of the quizz to retrieve questions for
     * @return list of questions
     */
    public List<Question> getQuestions(int quizzId) {
        List<Question> questions = client.target("http://localhost:8080/QuizzToolBackend/webapi/")
                .path("questions/" + quizzId)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Question>>() {
                });

        return questions;
    }

    /**
     * Sends all answers to the server
     *
     * @param userId The user that the answers belong to
     * @param quizzId The quizz which the user has answered
     * @param answers Ids of the answered alternative in a String format
     * @return The result of the quizz after it has been corrected by the server
     */
    public QuizzResult submitAnswers(int userId, int quizzId, String answers) {
        QuizzResult quizzResult = client.target("http://localhost:8080/QuizzToolBackend/webapi/")
                .path("quizzresults/" + userId + "&" + quizzId + "&" + answers)
                .request(MediaType.APPLICATION_JSON)
                .get(QuizzResult.class);

        return quizzResult;
    }

    /**
     * Retrieves all QuizzResults for a specific user
     *
     * @param userId The id of the user
     * @return list of QuizzResults
     */
    public List<QuizzResult> getUserQuizzResults(int userId) {
        List<QuizzResult> quizzResult = client.target("http://localhost:8080/QuizzToolBackend/webapi/")
                .path("quizzresults/userresults/" + userId)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<QuizzResult>>() {
                });

        return quizzResult;
    }

    /**
     * Sends a request to add a new Quizz
     *
     * @param quizzToAdd The quizz to add
     * @param courseId The course to which the quizz will be added to
     */
    public void addQuizz(Quizz quizzToAdd, int courseId) {
        client.target("http://localhost:8080/QuizzToolBackend/webapi/")
                .path("quizzes/" + courseId)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(quizzToAdd), Quizz.class);
    }

    /**
     * Retrieves all courses
     *
     * @return list of courses
     */
    public List<Course> getCourses() {
        List<Course> courses = client.target("http://localhost:8080/QuizzToolBackend/webapi/")
                .path("courses/")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Course>>() {
                });
        return courses;
    }

    /**
     * Retrieves the last quizz that was added
     *
     * @return
     */
    public Quizz getLastQuizz() {
        Quizz lastQuizz = client.target("http://localhost:8080/QuizzToolBackend/webapi/")
                .path("quizzes/lastQuizz")
                .request(MediaType.APPLICATION_JSON)
                .get(Quizz.class);
        return lastQuizz;
    }

    /**
     * Sends a request to add a new Question
     *
     * @param questionToAdd The question to add
     * @param quizzId The quizz to which the question will be added to
     */
    public void addQuestion(Question questionToAdd, int quizzId) {
        client.target("http://localhost:8080/QuizzToolBackend/webapi/")
                .path("questions/" + quizzId)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(questionToAdd), Question.class);
    }

    /**
     * Retrieves the last question that was added
     *
     * @return
     */
    public Question getLastQuestion() {
        Question lastQuestion = client.target("http://localhost:8080/QuizzToolBackend/webapi/")
                .path("questions/lastQuestion")
                .request(MediaType.APPLICATION_JSON)
                .get(Question.class);
        return lastQuestion;
    }

    /**
     * Sends a request to add a new alternative
     *
     * @param alternativeToAdd The alternative to add
     * @param questionId The question to which the alternative will be added to
     */
    public void addAlternative(Alternative alternativeToAdd, int questionId) {
        client.target("http://localhost:8080/QuizzToolBackend/webapi/")
                .path("alternatives/" + questionId)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(alternativeToAdd), Alternative.class);
    }

}
