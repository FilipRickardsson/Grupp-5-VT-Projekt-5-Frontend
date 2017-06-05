
package dialog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Dialog {
     
    public static void display(String title, String massage) {
        Stage window = new Stage();
        //show "info" in title rad längst upp i window-box
        window.setTitle(title);
        window.setMinWidth(400);
        window.setMinHeight(120);

        Label myLable = new Label();
        Label label1 = new Label();

        //vilken meddelande som skulle visa 
        myLable.setText(massage);
        myLable.setFont(Font.font("Amble CN", FontWeight.BOLD, 18));
        myLable.setTextFill(Color.web("#EE7202"));

        Button btnOK = new Button("OK");
   
        btnOK.setOnAction(e -> window.close());

        VBox myLayout = new VBox();
        
        myLayout.setPadding(new Insets(20, 30, 50, 50));
        myLayout.setSpacing(30);

        myLayout.getChildren().addAll(myLable, btnOK);
        myLayout.setAlignment(Pos.CENTER);

        Scene myScene = new Scene(myLayout);
        window.setScene(myScene);
        window.showAndWait();//Shows this stage and waits for it to be hidden (closed)

    }
    
}
