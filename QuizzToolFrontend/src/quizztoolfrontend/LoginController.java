package quizztoolfrontend;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.QuizzUser;
import servercommunication.ServerConnection;

public class LoginController implements Initializable {

    private ServerConnection serverConnection;

    @FXML
    private TextField tfUsername, tfPassword;

    @FXML
    private Label lblError;

    @FXML
    private void login() {
        try {
            if (tfUsername.getText().trim().length() > 0 && tfPassword.getText().length() > 0) {
                QuizzUser quissUser = serverConnection.getQuizzUser(tfUsername.getText(), tfPassword.getText());
                if (quissUser != null) {
                    System.out.println("Success!");
                    lblError.setText("Success! (Change this later to change scene instead)");
                } else {
                    throw new IllegalArgumentException();
                }
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            lblError.setText("Incorrect username or password.");
            tfPassword.setText("");
            System.out.println("Incorrect username or password.");
            
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serverConnection = ServerConnection.getServerConnection();
    }

}
