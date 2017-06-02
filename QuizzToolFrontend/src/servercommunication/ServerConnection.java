package servercommunication;

import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import models.Question;
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

    public synchronized QuizzUser getQuizzUser(String username, String password) {
        QuizzUser quizzUser = client.target("http://localhost:8080/QuizzToolBackend/webapi/")
                .path("quizz_users/" + username + "&" + password)
                .request(MediaType.APPLICATION_JSON)
                .get(QuizzUser.class);

        return quizzUser;
    }
   /* public synchronized Question getQuestion(int id){
        Question question= 
        client.target("http://localhost:8080/QuizzToolBackend/webapi/").
                path(Integer.toString(id)).
                request(MediaType.APPLICATION_JSON).
                get(Question.class);
        return question;
    } */
    
    public synchronized List<Question> getQuestions(int quizzId) {
        GenericType<List<Question>> questionsGeneric = new GenericType<List<Question>>(){};
        List<Question> questions = client.target("http://localhost:8080/QuizzToolBackend/webapi/questions/")
                .path(Integer.toString(quizzId)).request(MediaType.APPLICATION_JSON).get(questionsGeneric);
        return questions;
    }

}
