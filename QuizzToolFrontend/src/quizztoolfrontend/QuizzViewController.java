package quizztoolfrontend;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.Question;
import models.Quizz;
import servercommunication.ServerConnection;


public class QuizzViewController implements Initializable {

    @FXML
    private Label lbl_question;
    ServerConnection serverConnection=ServerConnection.getServerConnection();

    private int quizzId; 
    private List <Question> questions;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        Question question =   serverConnection.getQuestion(1);
  //      lbl_question.setText(question.getText());
         
    }    
    
   public void setQuizzId(int quizzId){
        this.quizzId=quizzId;
        this.questions= this.setQuestions(quizzId);
        
        System.out.println("quizzID nr:"+this.quizzId);
       System.out.println("list size?:"+ this.questions.size()); 
       
                }
   private List<Question> setQuestions(int quizzId){
       return new ArrayList<Question>(serverConnection.getQuestions(quizzId));
       
       
   }
    
   
}
