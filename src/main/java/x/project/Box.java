package x.project;

import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Box extends PhysicalObject {
    private double width;
    private double velocity;
    private double acceleration;
    private double mass;
    private double previousVelocity;
    private double frictionForce = 0.0;
    private double springForce = 0.0;
    private boolean joined = false;

    public Box(double x, double size, double mass, double velocity, double acceleration, Rectangle view) {
        super(view, x, 400);

        this.width = size;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.mass = mass;
        this.previousVelocity = velocity;

        view.setWidth(getWidth());
        view.setHeight(getWidth());
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

        incrementX(velocity * seconds * sceneScaleFactor);
    }
}
