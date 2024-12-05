package x.project;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowDescription {
    private final Box box;
    private final Pane pane;
    private Line arrow;
    private Line arrowTop;
    private Line arrowBottom;
    private Circle center;
    private Text textVelocity;
    private Text textAcceleration;
    private boolean showing;

    public ShowDescription(Pane pane, Box box) {
        this.pane = pane;
        this.box = box;
    }

    public void showAll() {
        if (!showing) {
            showing = true;
            showVelocity();
            showAcceleration();
        }
    }

    private void showAcceleration() {
        double acceleration = box.getAcceleration();
        double size = box.getWidth();
        double startX = box.getX() + size / 2;
        double startY = 400 + size / 2;
        textAcceleration = new Text(startX - size / 2, startY - size * 1.2, String.format("a = %.2f м/с^2", acceleration));
        textAcceleration.setFont(Font.font(12));
        pane.getChildren().add(textAcceleration);
    }

    private void showVelocity() {
        double velocity = box.getVelocity();
        double size = box.getWidth();
        double startX = box.getX() + size / 2;
        double startY = 400 + size / 2;
        double velocitySign = velocity < 0 ? -1.0 : 1.0;

        textVelocity = new Text(startX - size / 2, startY - size, String.format("v = %.2f м/с", velocity));
        textVelocity.setFont(Font.font(12));
        center = new Circle(startX, startY, 3);
        velocity *= 20.0;

        arrow = new Line(startX, startY, startX + velocity, startY);
        arrowTop = new Line(startX + velocity, startY, startX + velocity - 5 * velocitySign, startY - 5);
        arrowBottom = new Line(startX + velocity, startY, startX + velocity - 5 * velocitySign, startY + 5);

        if (Math.abs(velocity) > 1e-9) {
            pane.getChildren().addAll(arrow, arrowTop, arrowBottom);
        }

        pane.getChildren().addAll(textVelocity, center);
    }

    public void hideAll() {
        showing = false;
        hideVelocity();
        hideAcceleration();
    }

    private void hideAcceleration() {
        pane.getChildren().remove(textAcceleration);
    }

    private void hideVelocity() {
        pane.getChildren().removeAll(arrow, arrowTop, arrowBottom, textVelocity, center);
    }
}
