package x.project;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ObjectDescription implements DrawableObject {
    private final PhysicalObject physicalObject;
    private final Pane pane;
    private Line arrow;
    private Line arrowTop;
    private Line arrowBottom;
    private Circle center;
    private Text text;
    List<Box> boxesList;
    private boolean showing;

    @Getter
    @Setter
    private Mode mode;

    public ObjectDescription(Pane pane, PhysicalObject physicalObject, List<Box> boxesList, Mode mode) {
        this.pane = pane;
        this.physicalObject = physicalObject;
        this.boxesList = boxesList;
        this.mode = mode;
    }

    public void showAll() {
        if (!showing) {
            showing = true;

            if (physicalObject.isHideDescription()) {
                return;
            }

            double maxMass = boxesList.stream().map(Box::getMass).max(Double::compareTo).orElse(1.0);

            if (physicalObject instanceof Box box) {
                switch (mode) {
                    case VELOCITY -> showVelocity();
                    case ACCELERATION -> showAcceleration();
                    case FRICTION_FORCE -> showForce(box.getFrictionForce(), 75.0 / maxMass);
                    case SPRING_FORCE -> showForce(box.getSpringForce(), 75.0 / maxMass);
                }
            } else if (physicalObject instanceof Spring spring) {
                Box leftBox = spring.getLeftBox();
                Box rightBox = spring.getRightBox();
                double size = spring.getWidth() + leftBox.getWidth();
                double startY = leftBox.getOnScreenY() + leftBox.getWidth() / 2;

                double dotCenter = leftBox.getOnScreenX() + leftBox.getWidth() / 2 + size * rightBox.getMass() / (leftBox.getMass() + rightBox.getMass());
                center = new Circle(dotCenter, startY, 4);
                center.setFill(javafx.scene.paint.Color.RED);

                pane.getChildren().addAll(center);
            }
        }
    }

    private void drawVector(String message, double value) {
        double size = physicalObject.getWidth();
        double startX = physicalObject.getOnScreenX() + size / 2;
        double startY = physicalObject.getOnScreenY() + size / 2;
        double valueSign = value < 0 ? -1.0 : 1.0;

        text = new Text(startX - size / 2, startY - 80, message);
        text.setFont(Font.font(12));

        if (Math.abs(value) > 200) {
            value = 200 * valueSign;
        }

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
        if (physicalObject instanceof Box box) {
            double acceleration = box.getAcceleration();
            drawVector(String.format("a = %.2f м/с^2", acceleration), acceleration * 20.0);
        }
    }

    private void showVelocity() {
        if (physicalObject instanceof Box box) {
            double velocity = box.getVelocity();
            drawVector(String.format("v = %.2f м/с", velocity), velocity * 20.0);
        }
    }

    private void showForce(double force, double scale) {
        drawVector(String.format("F = %.2f Н", force), force * scale);
    }

    public void hideAll() {
        showing = false;
        pane.getChildren().removeAll(arrow, arrowTop, arrowBottom, text, center);
    }

    @Override
    public void draw() {
        hideAll();
        showAll();
    }
}
