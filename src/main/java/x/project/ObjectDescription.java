package x.project;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

public class ObjectDescription {
    private final Box box;
    private final Pane pane;
    private Line arrow;
    private Line arrowTop;
    private Line arrowBottom;
    private Circle center;
    private Text text;
    private boolean showing;

    @Getter
    @Setter
    private Mode mode;

    public ObjectDescription(Pane pane, Box box, Mode mode) {
        this.pane = pane;
        this.box = box;
        this.mode = mode;
    }

    public void showAll() {
        if (!showing) {
            showing = true;

            if (box.isJoined()) {
                return;
            }

            switch (mode) {
                case VELOCITY -> showVelocity();
                case ACCELERATION -> showAcceleration();
                case FRICTION_FORCE -> showForce(box.getFrictionForce(), 1.0);
                case SPRING_FORCE -> showForce(box.getSpringForce(), 1.0);
            }
        }
    }

    private void drawVector(String message, double value) {
        double size = box.getWidth();
        double startX = box.getOnScreenX() + size / 2;
        double startY = box.getOnScreenY() + size / 2;
        double valueSign = value < 0 ? -1.0 : 1.0;

        text = new Text(startX - size / 2, startY - 80, message);
        text.setFont(Font.font(12));

        center = new Circle(startX, startY, 3);
        arrow = new Line(startX, startY, startX + value, startY);
        arrowTop = new Line(startX + value, startY, startX + value - 5 * valueSign, startY - 5);
        arrowBottom = new Line(startX + value, startY, startX + value - 5 * valueSign, startY + 5);

        if (Math.abs(value) > 1e-9) {
            pane.getChildren().addAll(arrow, arrowTop, arrowBottom);
        }

        pane.getChildren().addAll(text, center);
    }

    private void showAcceleration() {
        double acceleration = box.getAcceleration();
        drawVector(String.format("a = %.2f м/с^2", acceleration), acceleration * 20.0);
    }

    private void showVelocity() {
        double velocity = box.getVelocity();
        drawVector(String.format("v = %.2f м/с", velocity), velocity * 20.0);
    }

    private void showForce(double force, double scale) {
        drawVector(String.format("F = %.2f Н", force), force * scale);
    }

    public void hideAll() {
        showing = false;
        pane.getChildren().removeAll(arrow, arrowTop, arrowBottom, text, center);
    }
}
