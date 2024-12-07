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
    private Text text;
    private boolean showing;
    private Mode mode;

    public ShowDescription(Pane pane, Box box, Mode mode) {
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
                case FRICTION_FORCE -> showForce(box.getFrictionForce(), 40.0 / box.getMass());
                case SPRING_FORCE -> showForce(box.getSpringForce(), 1.0);
            }
        }
    }

    private void showAcceleration() {
        double acceleration = box.getAcceleration();
        double size = box.getWidth();
        double startX = box.getOnScreenX() + size / 2;
        double startY = 400 + size / 2;
        double accelerationSign = acceleration < 0 ? -1.0 : 1.0;

        text = new Text(startX - size / 2, startY - 80, String.format("a = %.2f м/с^2", acceleration));
        text.setFont(Font.font(12));
        center = new Circle(startX, startY, 3);

        acceleration *= 20.0;

        arrow = new Line(startX, startY, startX + acceleration, startY);
        arrowTop = new Line(startX + acceleration, startY, startX + acceleration - 5 * accelerationSign, startY - 5);
        arrowBottom = new Line(startX + acceleration, startY, startX + acceleration - 5 * accelerationSign, startY + 5);
        if (Math.abs(acceleration) > 1e-9) {
            pane.getChildren().addAll(arrow, arrowTop, arrowBottom);
        }
        pane.getChildren().addAll(text, center);
    }

    private void showVelocity() {
        double velocity = box.getVelocity();
        double size = box.getWidth();
        double startX = box.getOnScreenX() + size / 2;
        double startY = 400 + size / 2;
        double velocitySign = velocity < 0 ? -1.0 : 1.0;

        text = new Text(startX - size / 2, startY - 80, String.format("v = %.2f м/с", velocity));
        text.setFont(Font.font(12));
        center = new Circle(startX, startY, 3);
        velocity *= 20.0;

        arrow = new Line(startX, startY, startX + velocity, startY);
        arrowTop = new Line(startX + velocity, startY, startX + velocity - 5 * velocitySign, startY - 5);
        arrowBottom = new Line(startX + velocity, startY, startX + velocity - 5 * velocitySign, startY + 5);

        if (Math.abs(velocity) > 1e-9) {
            pane.getChildren().addAll(arrow, arrowTop, arrowBottom);
        }
        pane.getChildren().addAll(text, center);
    }

    private void showForce(double force, double scale) {
        double size = box.getWidth();
        double startX = box.getOnScreenX() + size / 2;
        double startY = 400 + size / 2;
        double forceSign = force < 0 ? -1.0 : 1.0;

        text = new Text(startX - size / 2, startY - 80, String.format("F = %.2f Н", force));
        text.setFont(Font.font(12));

        force *= scale;

        center = new Circle(startX, startY, 3);
        arrow = new Line(startX, startY, startX + force, startY);

        arrowTop = new Line(startX + force, startY, startX + force - 5 * forceSign, startY - 5);
        arrowBottom = new Line(startX + force, startY, startX + force - 5 * forceSign, startY + 5);

        if (Math.abs(force) > 1e-9) {
            pane.getChildren().addAll(arrow, arrowTop, arrowBottom);
        }

        pane.getChildren().addAll(text, center);
    }

    public void hideAll() {
        showing = false;
        pane.getChildren().removeAll(arrow, arrowTop, arrowBottom, text, center);
    }
}
