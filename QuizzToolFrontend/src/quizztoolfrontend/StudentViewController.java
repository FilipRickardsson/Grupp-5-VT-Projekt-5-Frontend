package quizztoolfrontend;

import java.io.IOException;
import java.net.URL;
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

    @FXML
    private void onCourseChange(ActionEvent event) {
        Course course = (Course) cbCourses.getSelectionModel().getSelectedItem();
        cbQuizzes.getItems().clear();
        cbQuizzes.getItems().setAll(course.getQuizzes());
    }

    @FXML
    private void onQuizzChange(ActionEvent event) {
        Quizz quizz = (Quizz) cbQuizzes.getSelectionModel().getSelectedItem();
        if (quizz != null) {
            olResult.clear();
            boolean found = false;
            for (QuizzResult qr : quizzResults) {
                if (qr.getQuizz().getQuizzId() == quizz.getQuizzId()) {
                    olResult.add("Grade: " + qr.getGrade());
                    olResult.add("Points: " + qr.getPoints());
                    found = true;
                    startTest.setDisable(true);
                    break;
                }
            }

            if (!found) {
                selectedQuizz = quizz;
                olResult.add("No result. Click the button to start the Quizz.");
                startTest.setDisable(false);
            }
        } else {
            startTest.setDisable(true);
            olResult.clear();
        }

//        Quizz quizz = (Quizz) cbQuizzes.getSelectionModel().getSelectedItem();
//        if (quizz != null) {
//            selectedQuizz = quizz;
//            startTest.setDisable(false);
//        } else {
//            startTest.setDisable(true);
//        }
    }

    @FXML
    ToggleGroup toggleGroup;

    public void toggleGroupAction(ActionEvent action) {
        System.out.println("Toggle Group" + toggleGroup.getSelectedToggle().getUserData().toString());
    }

    @FXML
    private void startQuizz(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizzView.fxml"));
        Parent root = (Parent) loader.load();
        QuizzViewController controller = (QuizzViewController) loader.getController();
        controller.getQuestions(selectedQuizz.getQuizzId());
        controller.setUserId(userId);
        controller.setQuizzTitle(selectedQuizz.getName());
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void getResults() {
        quizzResults = ServerConnection.getServerConnection().getUserQuizzResults(userId);
    }

    public void setCoursesAndQuizzes(QuizzUser quizzUser) {
        cbCourses.getItems().addAll(quizzUser.getCourses());
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

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
