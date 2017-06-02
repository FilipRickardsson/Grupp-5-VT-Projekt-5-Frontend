package quizztoolfrontend;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Question;
import servercommunication.ServerConnection;

public class QuizzViewController implements Initializable {

    @FXML
    private TextField textField_QuizzName;
    @FXML
    private TextField textField_StartTime;
    @FXML
    private TextField textField_endTime;
    @FXML
    private DatePicker datePicker_date;

    //@FXML
    //private Label lbl_question;
   //ServerConnection serverConnection=ServerConnection.getServerConnection();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        Question question =   serverConnection.getQuestion(1);
  //      lbl_question.setText(question.getText());
    }    

    @FXML
    private void handlerBtnCreate(ActionEvent event) {
    }

    @FXML
    private void handlerBtnCancel(ActionEvent event) {
    }
    
    
}
