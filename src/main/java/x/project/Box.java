package x.project;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Box {
    private double x;
    private double width;
    private double velocity;
    private double acceleration;
    private double mass;
    private final Rectangle view;
    private double previousVelocity;

    public Box(double x, double size, double mass, double velocity, double acceleration, Rectangle view) {
        this.x = x;
        this.width = size;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.mass = mass;
        this.previousVelocity = velocity;

        view.setX(getX());
        view.setWidth(getWidth());
        view.setHeight(getWidth());
        view.setY(400);
        this.view = view;
    }

    public void addImpulse(double impulse) {
        velocity += impulse / getMass();
    }

    public double getImpulse() {
        return mass * velocity;
    }

    public double getKineticEnergy() {
        return 0.5 * mass * velocity * velocity;
    }

    public void move(double seconds, double sceneScaleFactor) {
        if (seconds > 0) {
            acceleration = (velocity - previousVelocity) / seconds;
            previousVelocity = velocity;
        }

        x += velocity * seconds * sceneScaleFactor;
        view.setX(x);
    }
}
