package servercommunication;

import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
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

    public synchronized QuizzUser getQuizzUser(String username, String password) {
        QuizzUser quizzUser = client.target("http://localhost:8080/QuizzToolBackend/webapi/")
                .path("quizz_users/" + username + "&" + password)
                .request(MediaType.APPLICATION_JSON)
                .get(QuizzUser.class);

        return quizzUser;
    }

    public synchronized List<QuizzResult> getQuizzResult(int quizzId) {
        System.out.println("DebaggorN 1: " + quizzId);

        List<QuizzResult> quizzResults = client.target("http://localhost:8080/QuizzToolBackend/webapi/")
                .path("quizzresults/" + quizzId)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<QuizzResult>>() {
                });

        return quizzResults;
    }

}
