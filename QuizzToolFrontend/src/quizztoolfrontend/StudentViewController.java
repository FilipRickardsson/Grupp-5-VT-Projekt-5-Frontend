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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import models.Course;
import models.Quizz;
import models.QuizzUser;

public class StudentViewController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private ComboBox cbCourses, cbQuizzes;

    @FXML
    private Button startTest;

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
            startTest.setDisable(false);
        } else {
            startTest.setDisable(true);
        }
    }

    @FXML
    ToggleGroup toggleGroup;

    public void toggleGroupAction(ActionEvent action) {
        System.out.println("Toggle Group" + toggleGroup.getSelectedToggle().getUserData().toString());
    }

    @FXML
    private void startQuizz(ActionEvent event) throws IOException {
        Parent root1 = FXMLLoader.load(getClass().getResource("QuizzView.fxml"));
        Scene scene = new Scene(root1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        

    }

    public void setCoursesAndQuizzes(QuizzUser quizzUser) {
        cbCourses.getItems().addAll(quizzUser.getCourses());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
