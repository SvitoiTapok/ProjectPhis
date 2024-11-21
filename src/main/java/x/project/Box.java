package x.project;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Box {
    private double x;
    private double size;
    private double velocity;
    private double acceleration;
    private Rectangle view;

    public Box(double x, double size, double velocity, double acceleration, Rectangle view) {
        this.x = x;
        this.size = size;
        this.velocity = velocity;
        this.acceleration = acceleration;

        view.setX(getX());
        view.setWidth(getSize());
        view.setHeight(getSize());
        view.setY(400);
        this.view = view;

    }
    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public double getSize() {
        return size;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getX() {
        return x;
    }

    public void move(long delta){
        //из нано в секунды
        double seconds = delta/1000000000.0;
        velocity = velocity + acceleration*seconds;
        x += velocity*seconds;
        view.setX(x);
    }
}
