import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
public class MultipleBallPane extends Pane {
    private Timeline animation;

    public MultipleBallPane() {
        // Create an animation for moving the ball
        animation = new Timeline(
                new KeyFrame(Duration.millis(50), e -> moveBall()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play(); // Start animation
    }

    public void add() {
        Color color = new Color(Math.random(),
                Math.random(), Math.random(), 0.5);
        getChildren().add(new Ball(30, 30, 5, color));
    }

    public void subtract() {
        if (getChildren().size() > 0) {
            getChildren().remove(getChildren().size() - 1);
        }
    }

    public void play() {
        animation.play();
    }

    public void pause() {
        animation.pause();
    }

    public void increaseSpeed() {
        animation.setRate(animation.getRate() + 0.1);
    }

    public void decreaseSpeed() {
        animation.setRate(
                animation.getRate() > 0 ? animation.getRate() - 0.1 : 0);
    }

    public DoubleProperty rateProperty() {
        return animation.rateProperty();
    }

    protected void moveBall() {
        for (Node node: this.getChildren()) {
            Ball ball = (Ball)node;
            // Check boundaries
            if (ball.getCenterX() < ball.getRadius() ||
                    ball.getCenterX() > getWidth() - ball.getRadius()) {
                ball.dx *= -1; // Change ball move direction
            }
            if (ball.getCenterY() < ball.getRadius() ||
                    ball.getCenterY() > getHeight() - ball.getRadius()) {
                ball.dy *= -1; // Change ball move direction
            }

            // Adjust ball position
            ball.setCenterX(ball.dx + ball.getCenterX());
            ball.setCenterY(ball.dy + ball.getCenterY());

            // Check if balls collide
            ArrayList<Node> list = new ArrayList<>(this.getChildren());
            for (int i = getChildren().indexOf(node) + 1;
                 i < list.size(); i++) {
                Ball nextBall = (Ball)list.get(i);
                if (intersects(ball, nextBall)) {
                    // Remove the later ball that was added to
                    // pane and add its radius to the other ball
                    ball.setRadius(ball.getRadius() +
                            nextBall.getRadius());
                    getChildren().remove(nextBall);
                }
            }
        }
    }

    /** Returns true if circles inIntersect */
    private boolean intersects(Ball ball, Ball nextBall) {
        return Math.sqrt(Math.pow(ball.getCenterX() - nextBall.getCenterX(), 2) +
                Math.pow(ball.getCenterY() - nextBall.getCenterY(), 2))
                <= ball.getRadius() + nextBall.getRadius();
    }
}
