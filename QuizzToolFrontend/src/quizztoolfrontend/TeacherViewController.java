package quizztoolfrontend;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import models.Course;
import models.GradeType;
import models.Quizz;
import models.QuizzResult;
import models.QuizzUser;
import servercommunication.ServerConnection;

public class TeacherViewController implements Initializable {

    private ServerConnection serverConnection;

    @FXML
    private ComboBox cbCourses, cbQuizzes;

    @FXML
    private ListView lwResult;
    
    @FXML
    private ObservableList<PieChart.Data> olResult;
    
    @FXML
    PieChart chGrades;

    public void setCoursesAndQuizzes(QuizzUser quizzUser) {
        cbCourses.getItems().addAll(quizzUser.getCourses());
    }

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
            List<QuizzResult> quizzResults = serverConnection.getQuizzResult(quizz.getQuizzId());
            if (quizzResults.size() > 0) {
                olResult.clear();

                int[] statistics = calcStatistics(quizzResults);
                
               // olResult.add(new PieChart.Data ("Total Points: ", statistics[0] ));
                double avg = statistics[0] * 1.0 / quizzResults.size();
                //olResult.add(new PieChart.Data ("Average Points: ", avg));
               // olResult.add("Grades:");
                olResult.add(new PieChart.Data ("IG: ", statistics[1]));
                olResult.add(new PieChart.Data ("G: ", statistics[2]));
                olResult.add(new PieChart.Data ("VG: ", statistics[3]));
                chGrades.setData(olResult);
            } else {
                olResult.clear();
                //olResult.add("Nothing submitted");
            }
        } else {
            olResult.clear();
        }
    }

    private int[] calcStatistics(List<QuizzResult> quizzResults) {
        int sum = 0;
        int ig = 0;
        int g = 0;
        int vg = 0;

        for (QuizzResult qr : quizzResults) {
            sum += qr.getPoints();
            if (qr.getGrade() == GradeType.IG) {
                ig++;
            } else if (qr.getGrade() == GradeType.G) {
                g++;
            } else {
                vg++;
            }
        }

        return new int[]{sum, ig, g, vg};
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serverConnection = ServerConnection.getServerConnection();

        olResult = FXCollections.observableArrayList();
        lwResult.setItems(olResult);
       
    }

}
