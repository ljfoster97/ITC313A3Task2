import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class Ball extends Circle {
    public double dx = 1, dy = 1;
    Ball(double x, double y, double radius, Color color) {
        super(x, y, radius);
        setFill(color); // Set ball color
    }
}