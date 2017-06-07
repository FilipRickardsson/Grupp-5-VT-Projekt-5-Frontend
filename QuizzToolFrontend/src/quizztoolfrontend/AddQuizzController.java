package quizztoolfrontend;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Alternative;
import models.Course;
import models.Question;
import models.Quizz;
import servercommunication.ServerConnection;

/**
 * Controller class for the AddQuizz Scene
 */
public class AddQuizzController implements Initializable {

    ServerConnection serverConnection;

    @FXML
    private TextField tfQuizzName, tfStartTime, tfStopTime;

    @FXML
    private CheckBox chbShowResult;

    @FXML
    private ComboBox cbCourses;

    @FXML
    private VBox vbContent;

    @FXML
    private HBox hbAddRemoveQuestion;

    @FXML
    private Button btnCreateQuizz;

    private List<VBox> questionContainers;

    private List<VBox> alternativeContainers;

    private int nbrOfQuestions;

    @FXML
    Label lblError;

    @FXML
    Button btnCancel;

    private boolean valid = true;

    /**
     * Adds all the components to the GUI for a new question
     */
    @FXML
    private void addQuestion() {
        VBox questionContainer = new VBox();
        questionContainers.add(questionContainer);
        questionContainer.getStyleClass().add("quizzContainer");

        Label questionHeader = new Label("Question " + (nbrOfQuestions + 1));
        TextArea questionText = new TextArea();

        VBox questionAlternatives = new VBox();
        questionAlternatives.setUserData(new Integer(nbrOfQuestions));
        questionAlternatives.getStyleClass().add("quizzContainer");
        alternativeContainers.add(questionAlternatives);

        TextField alternative1 = new TextField();
        alternative1.setPromptText("Alternative text");
        TextField alternative2 = new TextField();
        alternative2.setPromptText("Alternative text");

        HBox hbox = new HBox();
        hbox.getStyleClass().add("horizontalSpacing");

        Button btnAddAlternative = new Button("Add Alternative");
        btnAddAlternative.getStyleClass().add("actionButton");
        btnAddAlternative.setUserData(new Integer(nbrOfQuestions));
        btnAddAlternative.setOnAction((ActionEvent e) -> {
            addAlternative(e);
        });

        Button btnRemoveAlternative = new Button("Remove Alternative");
        btnRemoveAlternative.getStyleClass().add("actionButton");
        btnRemoveAlternative.setUserData(new Integer(nbrOfQuestions));
        btnRemoveAlternative.setOnAction((ActionEvent e) -> {
            removeAlternative(e);
        });

        questionContainer.getChildren().add(questionHeader);
        questionContainer.getChildren().add(questionText);

        questionAlternatives.getChildren().add(alternative1);
        questionAlternatives.getChildren().add(alternative2);

        questionContainer.getChildren().add(questionAlternatives);

        hbox.getChildren().add(btnAddAlternative);
        hbox.getChildren().add(btnRemoveAlternative);

        questionContainer.getChildren().add(hbox);

        vbContent.getChildren().add(questionContainer);

        hbAddRemoveQuestion.toFront();
        btnCreateQuizz.toFront();
        btnCancel.toFront();
        lblError.toFront();

        nbrOfQuestions++;
    }

    /**
     * Removes the last question in the GUI
     */
    @FXML
    private void removeQuestion() {
        if (questionContainers.size() > 1) {
            vbContent.getChildren().remove(questionContainers.get(questionContainers.size() - 1));
            alternativeContainers.remove(questionContainers.size() - 1);
            questionContainers.remove(questionContainers.size() - 1);
            nbrOfQuestions--;
        }
    }

    /**
     * Adds all the components to the GUI for a new alternative
     *
     * @param event
     */
    @FXML
    private void addAlternative(ActionEvent event) {
        TextField alternative = new TextField();
        alternative.setPromptText("Alternative text");

        Button btn = ((Button) event.getSource());
        Integer id = (Integer) btn.getUserData();

        VBox vbox = null;
        for (VBox vb : alternativeContainers) {
            int userdata = ((Integer) vb.getUserData()).intValue();
            if (((Integer) vb.getUserData()).intValue() == id.intValue()) {
                vbox = vb;
                break;
            }
        }

        if (vbox != null) {
            vbox.getChildren().add(alternative);
        } else {
            System.out.println("null");
        }
    }

    /**
     * Removes the last alternative in the GUI
     *
     * @param event
     */
    @FXML
    private void removeAlternative(ActionEvent event) {
        Button btn = ((Button) event.getSource());
        Integer id = (Integer) btn.getUserData();

        VBox foundAlternativeContainer = null;
        for (VBox alternativeContainer : alternativeContainers) {
            if (((Integer) alternativeContainer.getUserData()).intValue() == id.intValue()) {
                foundAlternativeContainer = alternativeContainer;
                break;
            }
        }

        if (foundAlternativeContainer != null && !(foundAlternativeContainer.getChildren().size() < 3)) {
            foundAlternativeContainer.getChildren().remove(foundAlternativeContainer.getChildren().size() - 1);
        }
    }

    @FXML
    private void createQuizz(ActionEvent event) {
        Button btn = (Button) event.getSource();
        valid = true;
        if (validateInput((Pane) btn.getParent())) {
            List<Question> questions = new ArrayList();
            int currentQuestion = 0;
            for (VBox vb : questionContainers) {
                Question question = new Question();

                for (Node node : vb.getChildrenUnmodifiable()) {
                    if (node instanceof TextArea) {
                        TextArea ta = (TextArea) node;
                        question.setText(ta.getText());
                    }
                }

                List<Alternative> alternatives = new ArrayList();

                for (Node node : alternativeContainers.get(currentQuestion).getChildrenUnmodifiable()) {
                    if (node instanceof TextField) {
                        Alternative alt = new Alternative();

                        TextField ta = (TextField) node;

                        if (ta.getText().substring(ta.getText().length() - 1).contains("#")) {
                            alt.setCorrect(true);
                            alt.setText(ta.getText().substring(0, ta.getText().length() - 1));
                        } else {
                            alt.setCorrect(false);
                            alt.setText(ta.getText());
                        }

                        alternatives.add(alt);
                    }
                }

                question.setAlternatives(alternatives);
                questions.add(question);
                currentQuestion++;
            }

            if (validateQuestionAlterntives(questions) && validatestartAndStopTime()) {

                Quizz quizz = new Quizz();
                quizz.setQuestions(questions);
                quizz.setName(tfQuizzName.getText());
                quizz.setShowResult(chbShowResult.isSelected());
                quizz.setStartTime(tfStartTime.getText());
                quizz.setStopTime(tfStopTime.getText());

                Course c = (Course) cbCourses.getSelectionModel().getSelectedItem();
                serverConnection.addQuizz(quizz, c.getId());

                Quizz lastQuizz = serverConnection.getLastQuizz();

                for (Question q : questions) {
                    serverConnection.addQuestion(q, lastQuizz.getQuizzId());
                    Question lastQuestion = serverConnection.getLastQuestion();

                    for (Alternative a : q.getAlternatives()) {
                        serverConnection.addAlternative(a, lastQuestion.getId());
                    }
                }

                loadPreviousScene(event);
            } else {
                lblError.setText("Check your input.");
            }
        } else {
            lblError.setText("Check your input.");
        }
    }

    /**
     * Validates the start and stop time inputted by the user
     *
     * @return true if valid input
     */
    private boolean validatestartAndStopTime() {
        boolean validStartAndStopTime = true;

        try {
            String startSplitted[] = tfStartTime.getText().split(":");
            String stopSplitted[] = tfStopTime.getText().split(":");

            int startHours = Integer.parseInt(startSplitted[0]);
            int startMinutes = Integer.parseInt(startSplitted[1]);

            int stopHours = Integer.parseInt(stopSplitted[0]);
            int stopMinutes = Integer.parseInt(stopSplitted[1]);

            validStartAndStopTime = startHours > -1 && startHours < 24 && stopHours > -1 && stopHours < 24 && startMinutes > -1 && startMinutes < 60 && stopMinutes > -1 && stopMinutes < 60 && startHours < stopHours;
        } catch (NumberFormatException e) {
            validStartAndStopTime = false;
        }

        return validStartAndStopTime;
    }

    /**
     * Validates that one and only one alternative is the correct one per
     * question
     *
     * @param questions the questions to validate
     * @return true if valid alternatives
     */
    private boolean validateQuestionAlterntives(List<Question> questions) {
        boolean validAlternatives = true;

        for (Question q : questions) {
            int trueCounter = 0;
            for (Alternative a : q.getAlternatives()) {
                if (a.isCorrect()) {
                    trueCounter++;
                }
            }
            if (trueCounter < 1 || trueCounter > 1) {
                validAlternatives = false;
                break;
            }
        }

        return validAlternatives;
    }

    /**
     * Loads the previous scene which is the TeacherView
     *
     * @param event
     */
    @FXML
    private void loadPreviousScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TeacherView.fxml"));
            Parent root = (Parent) loader.load();
            TeacherViewController controller = (TeacherViewController) loader.getController();
            controller.getCoursesAndQuizzes();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Validates the input for questions and alternatives are not empty and that
     * a course has been selected
     *
     * @param parent the node to which to search for textfields in
     * @return true if valid input
     */
    private boolean validateInput(Pane parent) {
        for (Node node : parent.getChildren()) {
            if (node instanceof Pane) {
                validateInput((Pane) node);
            } else if (node instanceof TextField) {
                TextField tf = (TextField) node;
                if (tf.getText().trim().length() < 1) {
                    valid = false;
                    break;
                }
            } else if (node instanceof TextArea) {
                TextArea ta = (TextArea) node;
                if (ta.getText().trim().length() < 1) {
                    valid = false;
                    break;
                }
            }
        }

        if (cbCourses.getSelectionModel().getSelectedItem() == null) {
            valid = false;
        }

        return valid;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serverConnection = ServerConnection.getServerConnection();
        cbCourses.getItems().setAll(serverConnection.getCourses());

        nbrOfQuestions = 0;
        questionContainers = new ArrayList();
        alternativeContainers = new ArrayList();
        addQuestion();
    }

}
