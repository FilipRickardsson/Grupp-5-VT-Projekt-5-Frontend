package quizztoolfrontend;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import models.Alternative;
import models.Question;
import servercommunication.ServerConnection;

public class QuizzViewController implements Initializable {

    private ServerConnection serverConnection = ServerConnection.getServerConnection();

    private List<Question> questions;

    private int currentQuestion = 0;

    @FXML
    private Label lblQuizzName, lblQuestion, lblQuestionText;

    @FXML
    private VBox vbAlternatives;

    @FXML
    private Button btnPrevious, btnNext;

    private List<Integer> answers = new ArrayList();

    public void getQuestions(int quizzId) {
        questions = serverConnection.getQuestions(quizzId);
        setQuestion();
    }

    public void setQuizzTitle(String quizzTitle) {
        lblQuizzName.setText(quizzTitle);
    }

    @FXML
    private void nextQuestion() {
        collectAnswer();

        if (currentQuestion + 1 <= questions.size()) {
            currentQuestion++;
            setQuestion();
        }
    }

    @FXML
    private void previousQuestion() {
        collectAnswer();

        if (currentQuestion - 1 >= 0) {
            currentQuestion--;
            setQuestion();
        }
    }

    private void setQuestion() {
        lblQuestion.setText("Question " + (currentQuestion + 1) + "/" + questions.size());

        Question question = questions.get(currentQuestion);

        vbAlternatives.getChildren().clear();

        ToggleGroup toggleGroupAlternatives = new ToggleGroup();
        for (Alternative a : question.getAlternatives()) {
            RadioButton rb = new RadioButton(a.getText());
            rb.setToggleGroup(toggleGroupAlternatives);
            rb.setUserData(a);
            vbAlternatives.getChildren().add(rb);
        }

        lblQuestionText.setText(question.getText());
        updatePreviousAndNextButtons();
    }

    private void updatePreviousAndNextButtons() {
        if (currentQuestion == 0) {
            btnPrevious.setDisable(true);
            btnNext.setDisable(false);
        } else if (currentQuestion + 1 == questions.size()) {
            btnPrevious.setDisable(false);
            btnNext.setDisable(true);
        } else {
            btnPrevious.setDisable(false);
            btnNext.setDisable(false);
        }
    }

    private void collectAnswer() {
//        System.out.println(((RadioButton) item).isSelected()
        Alternative chosenAlternative = null;
        System.out.println("--------");
        vbAlternatives.getChildren().forEach(item -> {
            if (((RadioButton) item).isSelected()) {
                chosenAlternative = (Alternative)item.getUserData();
            }
        });
        System.out.println("--------");

        answers.add(currentQuestion, chosenAlternative.getAlternativeId());
    }

    @FXML
    private void submit() {
        collectAnswer();
        System.out.println("Submit!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
