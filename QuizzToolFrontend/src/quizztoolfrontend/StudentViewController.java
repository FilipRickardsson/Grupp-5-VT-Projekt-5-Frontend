package quizztoolfrontend;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
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
    private void test(ActionEvent event) {
        Course course = (Course)cbCourses.getSelectionModel().getSelectedItem();
        cbQuizzes.getItems().clear();
        cbQuizzes.getItems().setAll(course.getQuizzes());
        Quizz quizz = (Quizz)cbQuizzes.getSelectionModel().getSelectedItem();
        
    }
    
    @FXML
    private void enableStartTest(ActionEvent event) {
        Quizz quizz = (Quizz)cbQuizzes.getSelectionModel().getSelectedItem();
        
        startTest.setDisable(false);
        
    }
    
    @FXML ToggleGroup toggleGroup;
    public void toggleGroupAction(ActionEvent action){
        System.out.println("Toggle Group" + toggleGroup.getSelectedToggle().getUserData().toString());      
        
    }
    
    @FXML
    private void startQuizz(ActionEvent event) {
        
    }
    
    
    public void setCoursesAndQuizzes(QuizzUser quizzUser) {
        cbCourses.getItems().addAll(quizzUser.getCourses());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
