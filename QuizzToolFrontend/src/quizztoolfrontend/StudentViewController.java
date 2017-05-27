package quizztoolfrontend;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import models.Course;
import models.QuizzUser;

public class StudentViewController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private ComboBox cbCourses, cbQuizzes;
    
    
    @FXML
    private void test(ActionEvent event) {
        Course course = (Course)cbCourses.getSelectionModel().getSelectedItem();
        cbQuizzes.getItems().clear();
        cbQuizzes.getItems().setAll(course.getQuizzes());
    }
    
    public void setCoursesAndQuizzes(QuizzUser quizzUser) {
        cbCourses.getItems().addAll(quizzUser.getCourses());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
