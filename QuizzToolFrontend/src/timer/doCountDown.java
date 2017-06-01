/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author _o0
 */
public class doCountDown extends Application{
    
    private final Integer startTime = 10;
    private Integer seconds = startTime;
    private Label label;

    @Override
    public void start(Stage windows) throws Exception {
        
        Group root = new Group();
        label = new Label();
        label.setTextFill(Color.ORANGE);
        label.setFont(Font.font(20));
        HBox layout = new HBox(5);
        layout.getChildren().add(label);
        layout.setLayoutX(40);
        root.getChildren().add(layout);
        doTime();
        
        windows.setScene(new Scene(layout, 300, 70, Color.BLACK));
        windows.setTitle("Count down timer");
        windows.show();
    }
    
    

    private void doTime() {
        Timeline time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);
            if(time!=null){
                time.stop();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Sayonara sunshine, no more answering for you");
            }
        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                
                seconds--;
                  label.setText("Countdown: " + seconds.toString());
                        if(seconds<=0){
                            time.stop();
                        }
            }
            
        
            
        });
        time.getKeyFrames().add(frame);
        time.playFromStart();
    }
    
    public static void main(String[] args){
        Application.launch(args);
    }
}
