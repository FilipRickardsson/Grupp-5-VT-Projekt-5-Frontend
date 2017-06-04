package quizztoolfrontend;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Alternative;
import models.Course;
import models.Question;
import models.Quizz;
import servercommunication.ServerConnection;

public class AddQuizzController implements Initializable {

    @FXML
    private TextField tfQuizzName;

    @FXML
    private CheckBox chbShowResult;

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

        nbrOfQuestions++;
    }

    @FXML
    private void removeQuestion() {
        if (questionContainers.size() > 1) {
            vbContent.getChildren().remove(questionContainers.get(questionContainers.size() - 1));
            questionContainers.remove(questionContainers.size() - 1);
            nbrOfQuestions--;
        }
    }

    @FXML
    private void addAlternative(ActionEvent event) {
        TextField alternative = new TextField();
        alternative.setPromptText("Alternative text");

        Button btn = ((Button) event.getSource());
        Integer id = (Integer) btn.getUserData();

        VBox vbox = null;
        for (VBox vb : alternativeContainers) {
            if (((Integer) vb.getUserData()).intValue() == id.intValue()) {
                vbox = vb;
                break;
            }
        }

        if (vbox != null) {
            vbox.getChildren().add(alternative);
        }
    }

    @FXML
    private void removeAlternative(ActionEvent event) {
        Button btn = ((Button) event.getSource());
        Integer id = (Integer) btn.getUserData();

        VBox vbox = null;
        for (VBox vb : alternativeContainers) {
            if (((Integer) vb.getUserData()).intValue() == id.intValue()) {
                vbox = vb;
                break;
            }
        }

        if (vbox != null && !(vbox.getChildren().size() < 3)) {
            vbox.getChildren().remove(vbox.getChildren().size() - 1);
        }
    }

    @FXML
    private void createQuizz() {
        System.out.println("create Quizz");

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
                    alt.setText(ta.getText() + "");
                    alt.setCorrect(false);
//                    alt.setQuestions(questions);
                    alternatives.add(alt);
                }
            }

            question.setAlternatives(alternatives);
            questions.add(question);
            currentQuestion++;
        }

        Quizz quizz = new Quizz();
        quizz.setQuestions(questions);
        quizz.setName("Placeholder");
        quizz.setShowResult(true);
        List<Course> courses = new ArrayList();
        Course course = new Course();
        course.setId(1);
        courses.add(course);
        quizz.setCourses(courses);

        ServerConnection.getServerConnection().addQuizz(quizz);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nbrOfQuestions = 0;
        questionContainers = new ArrayList();
        alternativeContainers = new ArrayList();
        addQuestion();
    }

}
