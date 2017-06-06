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

/**
 * Controller class for the QuizzView Scene
 */
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

    /**
     * Retrieves all the questions from the server for the current quizz
     *
     * @param quizzId The id of the current quizz
     */
    public void getQuestions(int quizzId) {
        this.quizzId = quizzId;
        questions = serverConnection.getQuestions(quizzId);
        initQuizzAnsers();
        setQuestion();
    }

    /**
     * Sets the user id
     *
     * @param userId id of the user
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Initialises the answer list with incorrect answers
     */
    private void initQuizzAnsers() {
        for (int i = 0; i < questions.size(); i++) {
            answers.add(-1);
        }
    }

    /**
     * Sets the title of the quizz
     *
     * @param quizzTitle The title of the quizz
     */
    public void setQuizzTitle(String quizzTitle) {
        lblQuizzName.setText(quizzTitle);
    }

    /**
     * Switches to the next question
     */
    @FXML
    private void nextQuestion() {
        collectAnswer();

        if (currentQuestion + 1 <= questions.size()) {
            currentQuestion++;
            setQuestion();
        }
    }

    /**
     * Switches to the previous question
     */
    @FXML
    private void previousQuestion() {
        collectAnswer();

        if (currentQuestion - 1 >= 0) {
            currentQuestion--;
            setQuestion();
        }
    }

    /**
     * Clears, updates and creates the necessary components for a question
     */
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

    /**
     * Updates the disabled states of the previous and next buttons
     */
    private void updatePreviousAndNextButtons() {
        if (currentQuestion == 0 && questions.size() == 1) {
            btnPrevious.setDisable(true);
            btnNext.setDisable(true);
        } else if (currentQuestion == 0) {
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

    /**
     * Collects the selected answer from the GUI
     */
    private void collectAnswer() {
        for (Node node : vbAlternatives.getChildrenUnmodifiable()) {
            if (node instanceof RadioButton) {
                RadioButton rb = (RadioButton) node;
                if (rb.isSelected()) {
                    answers.set(currentQuestion, ((Alternative) rb.getUserData()).getAlternativeId());
                }
            }
        }
    }

    /**
     * Submits the user answers to the server and shows the result in the GUI if
     * it is allowed
     */
    @FXML
    public void submit() {
        collectAnswer();

        String serializedAnswers = answers.toString().replace("[", "").replace("]", "").replace(" ", "").trim();
        QuizzResult quizzResult = serverConnection.submitAnswers(userId, quizzId, serializedAnswers);

        Label lblHeader = new Label("The quizz has been submitted.");
        lblHeader.getStyleClass().add("header");
        vbQuizzContainer.getChildren().clear();

        vbQuizzContainer.getChildren().add(lblHeader);

        if (quizzResult != null) {
            Label lblResult = new Label("Your Result:\nGrade: " + quizzResult.getGrade() + "\nPoints: " + quizzResult.getPoints());
            vbQuizzContainer.getChildren().add(lblResult);
        }

    }

    /**
     * Loads the login scene
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

    /**
     * Calculated the time left and starts the timer
     * @param stopTime The stop time of the quizz
     */
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
    }

    /**
     * Updates the clock with how much time is left
     * @param timeLeft The time left
     */
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
