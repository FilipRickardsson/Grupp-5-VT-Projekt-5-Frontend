package quizztoolfrontend;

import javafx.application.Platform;

/**
 * Handles the countdown to when the quizz must be submitted
 */
public class QuizzTimer extends Thread {

    private int secondsLeft;
    private QuizzViewController quizzViewController;

    public QuizzTimer(int secondsLeft, QuizzViewController quizzViewController) {
        this.secondsLeft = secondsLeft;
        this.quizzViewController = quizzViewController;
    }

    /**
     * Calculates how much time is left and updates the GUI
     */
    @Override
    public void run() {
        while (secondsLeft > 0) {
            try {
                int tempSecondsLeft = secondsLeft;
                int hours = tempSecondsLeft / 3600;
                tempSecondsLeft = tempSecondsLeft - hours * 3600;
                int minutes = tempSecondsLeft / 60;
                tempSecondsLeft = tempSecondsLeft - minutes * 60;
                int seconds = tempSecondsLeft;
                secondsLeft--;
                quizzViewController.setTimeLeft(hours + ":" + minutes + ":" + seconds);
                
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                quizzViewController.submit();
            }
        });
    }

}
