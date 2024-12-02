package x.project;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CameraMover {
    public static final CameraMover CAMERA_MOVER = new CameraMover();
    public static final double OFFSET = 5.0;

    private boolean movingLeft = false;
    private boolean movingRight = false;

    public void moveBox(Box box) {
        if (movingRight) {
            box.setX(box.getX() + OFFSET);
        }

        if (movingLeft) {
            box.setX(box.getX() - OFFSET);
        }
    }
}
