package x.project;

import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SceneObject {
    private final Rectangle view;
    private double x = 0.0;
    private double y = 0.0;

    @Setter
    private boolean xUpdatable = true;

    SceneObject(Rectangle view, double x, double y) {
        this.view = view;
        setX(x);
        setY(y);
    }

    public void setX(double x) {
        if (!xUpdatable) {
            return;
        }

        this.x = x;
        view.setX(x + CameraMover.CAMERA_MOVER.getCameraX());
    }

    public void setY(double y) {
        this.y = y;
        view.setY(y + CameraMover.CAMERA_MOVER.getCameraY());
    }

    public double getWidth() {
        return view.getWidth();
    }

    public double getHeight() {
        return view.getHeight();
    }

    public void incrementX(double dx) {
        setX(x + dx);
    }

    public double getOnScreenX() {
        return view.getX();
    }

    public double getOnScreenY() {
        return view.getY();
    }

    public void updateOnScreenPosition() {
        if (xUpdatable) {
            view.setX(x + CameraMover.CAMERA_MOVER.getCameraX());
        }

        view.setY(y + CameraMover.CAMERA_MOVER.getCameraY());
    }
}
