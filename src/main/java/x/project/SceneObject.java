package x.project;

import javafx.scene.shape.Rectangle;
import lombok.Getter;

@Getter
public class SceneObject {
    private final Rectangle view;
    private double x = 0.0;
    private double y = 0.0;

    SceneObject(Rectangle view, double x, double y) {
        this.view = view;
        setX(x);
        setY(y);
    }

    public void setX(double x) {
        this.x = x;
        view.setX(x + CameraMover.CAMERA_MOVER.getCameraX());
    }
    public void setY(double y) {
        this.y = y;
        view.setY(y + CameraMover.CAMERA_MOVER.getCameraY());
    }

    public void incrementX(double dx) {
        setX(x + dx);
    }

    public double getOnScreenX() {
        return view.getX();
    }

    public void updateOnScreenPosition() {
        view.setX(x + CameraMover.CAMERA_MOVER.getCameraX());
        view.setY(y + CameraMover.CAMERA_MOVER.getCameraY());
    }
}
