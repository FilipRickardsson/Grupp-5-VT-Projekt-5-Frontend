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
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
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
    private ObservableList<String> olResult;

    @FXML
    private ObservableList<PieChart.Data> olPie;

    @FXML
    PieChart chGrades;

    public void setCoursesAndQuizzes(QuizzUser quizzUser) {
        cbCourses.getItems().addAll(quizzUser.getCourses());
    }

    @FXML
    private void onCourseChange(ActionEvent event) {
        Course course = (Course) cbCourses.getSelectionModel().getSelectedItem();
        cbQuizzes.getItems().clear();
        olResult.clear();
        olPie.clear();
        cbQuizzes.getItems().setAll(course.getQuizzes());
    }

    @FXML
    private void onQuizzChange(ActionEvent event) {
        Quizz quizz = (Quizz) cbQuizzes.getSelectionModel().getSelectedItem();
        if (quizz != null) {
            List<QuizzResult> quizzResults = serverConnection.getQuizzResult(quizz.getQuizzId());
            if (quizzResults.size() > 0) {
                olResult.clear();
                olPie.clear();

                int[] statistics = calcStatistics(quizzResults);
                double avg = statistics[0] * 1.0 / quizzResults.size();

                olResult.add("Average Points: " + avg);

                olResult.add("Individual Grades:");
                for (QuizzResult qr : quizzResults) {
                    olResult.add("Name: " + ", Grade: " + qr.getGrade() + ", Points: " + qr.getPoints());
                }

                if (statistics[1] > 0) {
                    olPie.add(new PieChart.Data("IG: " + statistics[1], statistics[1]));
                }

                System.out.println(statistics[2]);
                if (statistics[2] > 0) {
                    olPie.add(new PieChart.Data("G: " + statistics[2], statistics[2]));
                }

                if (statistics[3] > 0) {
                    olPie.add(new PieChart.Data("VG: " + statistics[3], statistics[3]));
                }

            } else {
                olPie.clear();
                olResult.clear();
                olResult.add("Nothing submitted");
            }
        } else {
            olPie.clear();
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
        serverConnection = ServerConnection.getServerConnection();

        olResult = FXCollections.observableArrayList();
        olPie = FXCollections.observableArrayList();

        lwResult.setItems(olResult);
        chGrades.setData(olPie);
    }

}
