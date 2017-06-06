package quizztoolfrontend;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import models.Course;
import models.Quizz;
import models.QuizzResult;
import models.QuizzUser;
import servercommunication.ServerConnection;

/**
 * Controller class for the StudentView Scene
 */
public class StudentViewController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private ComboBox cbCourses, cbQuizzes;

    @FXML
    private Button startTest;

    private Quizz selectedQuizz;

    private int userId;

    private List<QuizzResult> quizzResults;

    @FXML
    private ListView lvResult;

    @FXML
    private ObservableList<String> olResult;

    /**
     * Updated the list of available quizzes when the course changes
     *
     * @param event
     */
    @FXML
    private void onCourseChange(ActionEvent event) {
        Course course = (Course) cbCourses.getSelectionModel().getSelectedItem();
        cbQuizzes.getItems().clear();
        cbQuizzes.getItems().setAll(course.getQuizzes());
    }

    /**
     * Updates the GUI based on the selected quizz
     *
     * @param event
     */
    @FXML
    private void onQuizzChange(ActionEvent event) {
        Quizz quizz = (Quizz) cbQuizzes.getSelectionModel().getSelectedItem();
        if (quizz != null) {
            olResult.clear();
            boolean found = false;
            // Searches for previous results
            for (QuizzResult qr : quizzResults) {
                if (qr.getQuizz().getQuizzId() == quizz.getQuizzId()) {
                    // Shows the results if it has been allowed
                    if (quizz.isShowResult()) {
                        olResult.add("Grade: " + qr.getGrade());
                        olResult.add("Points: " + qr.getPoints());
                    } else {
                        olResult.add("Your test has been submitted.");
                    }
                    found = true;
                    startTest.setDisable(true);

                    break;
                }
            }

            // If no previous result was found present the option to take the quizz if within the timelimit
            if (!found) {
                String now = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
                String splittedNow[] = now.split(":");
                String splittedStartTime[] = quizz.getStartTime().split(":");

                int nowInSeconds = Integer.parseInt(splittedNow[0]) * 3600 + Integer.parseInt(splittedNow[1]) * 60;
                int startTimeInSeconds = Integer.parseInt(splittedStartTime[0]) * 3600 + Integer.parseInt(splittedStartTime[1]) * 60;

                if (nowInSeconds > startTimeInSeconds) {
                    selectedQuizz = quizz;
                    olResult.add("No result. Click the button to start the Quizz.");
                    startTest.setDisable(false);
                } else {
                    olResult.add("You can't access this quizz before " + quizz.getStartTime());
                    startTest.setDisable(true);
                }
            }
        } else {
            startTest.setDisable(true);
            olResult.clear();
        }

    }

    @FXML
    ToggleGroup toggleGroup;

    public void toggleGroupAction(ActionEvent action) {
        System.out.println("Toggle Group" + toggleGroup.getSelectedToggle().getUserData().toString());
    }

    /**
     * Loads the QuizzView and sends required information to the scene
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void startQuizz(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizzView.fxml"));
        Parent root = (Parent) loader.load();
        QuizzViewController controller = (QuizzViewController) loader.getController();

        // Sends questions, the id of the current user, quizz title and start and stop time to the quizz scene
        controller.getQuestions(selectedQuizz.getQuizzId());
        controller.setUserId(userId);
        controller.setQuizzTitle(selectedQuizz.getName());
        controller.setStartAndEnd(selectedQuizz.getStopTime());

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    /**
     * Retrieves all the results from the server for a specific user
     */
    public void getResults() {
        quizzResults = ServerConnection.getServerConnection().getUserQuizzResults(userId);
    }

    /**
     * Sets all the courses and quizzes for a specific user
     *
     * @param quizzUser the current user
     */
    public void setCoursesAndQuizzes(QuizzUser quizzUser) {
        cbCourses.getItems().addAll(quizzUser.getCourses());
    }

    /**
     * Sets the user id
     *
     * @param userId The id of the current user
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Loads the Login scene
     *
     * @param event
     */
    @FXML
    private void logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        olResult = FXCollections.observableArrayList();
        lvResult.setItems(olResult);
    }

}
