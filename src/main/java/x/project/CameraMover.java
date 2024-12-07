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
    private boolean movingDown = false;
    private boolean movingUp = false;

    private double cameraX = 0.0;
    private double cameraY = 0.0;

    public void handleMove() {
        if (movingRight) {
            cameraX += OFFSET;
        }

        if (movingLeft) {
            cameraX -= OFFSET;
        }
        if (movingUp) {
            cameraY -= OFFSET;
        }
        if (movingDown) {
            cameraY += OFFSET;
        }
    }
}
