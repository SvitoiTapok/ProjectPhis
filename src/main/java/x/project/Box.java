package x.project;

import javafx.scene.shape.Rectangle;

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

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getMass() {
        return mass;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public double getWidth() {
        return width;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getX() {
        return x;
    }

    public void addImpulse(double impulse) {
        velocity += impulse / getMass();
    }

    public double getImpulse() {
        return mass * velocity;
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
