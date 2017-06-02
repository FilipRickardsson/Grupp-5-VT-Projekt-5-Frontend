package quizztoolfrontend;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TeacherViewController implements Initializable {

   

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    

    @FXML
    private void handketBtnNewQuizz(ActionEvent event) throws IOException {
         Parent root1 = FXMLLoader.load(getClass().getResource("NewQuizz.fxml"));
        Scene scene = new Scene(root1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }
    
}
