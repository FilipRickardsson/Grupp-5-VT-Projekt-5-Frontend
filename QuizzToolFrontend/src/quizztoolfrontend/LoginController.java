package quizztoolfrontend;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.QuizzUser;
import servercommunication.ServerConnection;

/**
 * Controller class for the Login Scene
 */
public class LoginController implements Initializable {

    private ServerConnection serverConnection;

    @FXML
    private TextField tfUsername, tfPassword;

    @FXML
    private Label lblError;

    /**
     * Tries to login by asking the server if the user exist based on username
     * and password inputted by the user
     *
     * @param event
     */
    @FXML
    private void login(ActionEvent event) {
        try {
            if (tfUsername.getText().trim().length() > 0 && tfPassword.getText().length() > 0) {
                QuizzUser quizzUser = serverConnection.getQuizzUser(tfUsername.getText(), tfPassword.getText());
                if (quizzUser != null) {
                    LoggedInCredentials.setUsername(tfUsername.getText());
                    LoggedInCredentials.setPassword(tfPassword.getText());

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    loadStudentOrTeacherScene(stage, quizzUser);
                } else {
                    throw new IllegalArgumentException();
                }
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            lblError.setText("Incorrect username or password.");
            tfPassword.setText("");
        }
    }

    /**
     * Loads the Student or Teacher view based on which role the user has
     *
     * @param stage
     * @param quizzUser the logged in QuizzUser
     */
    private void loadStudentOrTeacherScene(Stage stage, QuizzUser quizzUser) {
        try {
            Parent root;
            if (quizzUser.getQuizzUserType() == QuizzUserType.STUDENT) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentView.fxml"));
                root = (Parent) loader.load();
                StudentViewController controller = (StudentViewController) loader.getController();
                controller.setCoursesAndQuizzes(quizzUser);
                controller.setUserId(quizzUser.getId());
                controller.getResults();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("TeacherView.fxml"));
                root = (Parent) loader.load();
                TeacherViewController controller = (TeacherViewController) loader.getController();
                controller.setCoursesAndQuizzes(quizzUser);
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serverConnection = ServerConnection.getServerConnection();
    }

}
