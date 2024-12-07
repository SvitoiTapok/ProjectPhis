package x.project;

import javafx.scene.shape.Rectangle;
import lombok.Getter;

@Getter
public class SceneObject {
    private final Rectangle view;
    private double x = 0.0;

    SceneObject(Rectangle view, double x) {
        this.view = view;
        setX(x);
    }

    public void setX(double x) {
        this.x = x;
        view.setX(x + CameraMover.CAMERA_MOVER.getCameraX());
    }

    public void incrementX(double dx) {
        setX(x + dx);
    }

    public double getOnScreenX() {
        return view.getX();
    }

    public void updateOnScreenPosition() {
        view.setX(x + CameraMover.CAMERA_MOVER.getCameraX());
    }
}
