package controllers;

import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class LogoController implements Initializable {
    @FXML
    private ImageView logo;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Animation ----------- ----------- -----------
        logo.setVisible(true);

        RotateTransition rotate = new RotateTransition();
        rotate.setNode(logo);
        rotate.setDuration(Duration.millis(1000));
        rotate.setCycleCount(TranslateTransition.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setByAngle(360);
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.play();

        PauseTransition visiblePause = new PauseTransition(
                Duration.seconds(3)
        );
        visiblePause.setOnFinished(
                event -> logo.setVisible(false)
        );
        visiblePause.play();
        //          ----------- ----------- -----------
    }
}
