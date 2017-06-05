package quizztoolfrontend;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Alternative;
import models.Question;
import models.QuizzResult;
import servercommunication.ServerConnection;

public class QuizzViewController implements Initializable {

    private ServerConnection serverConnection = ServerConnection.getServerConnection();

    private List<Question> questions;

    private int currentQuestion = 0;

    @FXML
    private Label lblQuizzName, lblQuestion, lblQuestionText;

    @FXML
    private VBox vbAlternatives, vbQuizzContainer;

    @FXML
    private Button btnPrevious, btnNext;

    private List<Integer> answers = new ArrayList();

    private int quizzId;

    private int userId;

    @FXML
    Label lblTimeLeft;

    public void getQuestions(int quizzId) {
        this.quizzId = quizzId;
        questions = serverConnection.getQuestions(quizzId);
        initQuizzAnsers();
        setQuestion();
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private void initQuizzAnsers() {
        for (int i = 0; i < questions.size(); i++) {
            answers.add(-1);
        }
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
        System.out.println("--------");

        for (Node node : vbAlternatives.getChildrenUnmodifiable()) {
            if (node instanceof RadioButton) {
                RadioButton rb = (RadioButton) node;
                if (rb.isSelected()) {
                    answers.set(currentQuestion, ((Alternative) rb.getUserData()).getAlternativeId());
                }
            }
        }

        for (Integer i : answers) {
            System.out.println(i);
        }

//        answers.add(currentQuestion, chosenAlternative.getAlternativeId());
        System.out.println("--------");
    }

    @FXML
    public void submit() {
        collectAnswer();

        String serializedAnswers = answers.toString().replace("[", "").replace("]", "").replace(" ", "").trim();
        System.out.println(serializedAnswers);

        QuizzResult quizzResult = serverConnection.submitAnswers(userId, quizzId, serializedAnswers);

        Label lblHeader = new Label("The quizz has been submitted.");
        lblHeader.getStyleClass().add("header");
        vbQuizzContainer.getChildren().clear();

        vbQuizzContainer.getChildren().add(lblHeader);

        if (quizzResult != null) {
            System.out.println("Debaggor 4: " + quizzResult.toString());
            Label lblResult = new Label("Your Result:\nGrade: " + quizzResult.getGrade() + "\nPoints: " + quizzResult.getPoints());
            vbQuizzContainer.getChildren().add(lblResult);
        }

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

    public void setStartAndEnd(String stopTime) {
        String now = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());

        String nowSplitted[] = now.split(":");
        String stopSplitted[] = stopTime.split(":");

        int nowSeconds = Integer.parseInt(nowSplitted[0]) * 3600
                + Integer.parseInt(nowSplitted[1]) * 60;
        int stopSeconds = Integer.parseInt(stopSplitted[0]) * 3600
                + Integer.parseInt(stopSplitted[1]) * 60;

        int secondsLeft = stopSeconds - nowSeconds;

        QuizzTimer qt = new QuizzTimer(secondsLeft, this);
        qt.start();

//        lblTimeLeft.setText("Now: " + nowSeconds + " StopSeconds: " + stopSeconds + " SecondsLeft: " + secondsLeft);
    }

    public void setTimeLeft(String timeLeft) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lblTimeLeft.setText("Time Left: " + timeLeft);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
