import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.collections.ObservableList;
import java.util.*;

public class Task2 extends Application {
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        MultipleBallPane ballPane = new MultipleBallPane();
        ballPane.setStyle("-fx-border-color: yellow");

        // Create four buttons
        Button btSuspend = new Button("Suspend");
        Button btResume = new Button("Resume");
        Button btAdd = new Button("Add Ball");
        Button btSubtract = new Button("Remove Ball");
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(
                btSuspend, btResume, btAdd, btSubtract);
        hBox.setAlignment(Pos.CENTER);

        // Add or remove a ball
        btAdd.setOnAction(e -> ballPane.add());
        btSubtract.setOnAction(e -> ballPane.subtract());
        ballPane.setOnMousePressed(e -> {
            for (Node node : ballPane.getChildren()) {
                Ball ball = (Ball) node;
                if (ball.contains(e.getX(), e.getY())) {
                    ballPane.getChildren().remove(ball);
                }
            }
        });

        // Pause and resume animation
        btSuspend.setOnAction(e -> ballPane.pause());
        btResume.setOnAction(e -> ballPane.play());

        // Use a scroll bar to control animation speed
        ScrollBar sbSpeed = new ScrollBar();
        sbSpeed.setMax(20);
        sbSpeed.setValue(10);
        ballPane.rateProperty().bind(sbSpeed.valueProperty());

        // Create a border pane
        BorderPane pane = new BorderPane();
        pane.setCenter(ballPane);
        pane.setTop(sbSpeed);
        pane.setBottom(hBox);

        // Create a scene and place the in the stage
        Scene scene = new Scene(pane, 350, 200);
        primaryStage.setTitle("Combine Colliding Bouncing Balls"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }
}
