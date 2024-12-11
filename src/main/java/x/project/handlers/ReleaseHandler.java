package x.project.handlers;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import x.project.CameraMover;

public class ReleaseHandler implements EventHandler<KeyEvent> {
    private final CameraMover cameraMover;

    public ReleaseHandler(CameraMover cameraMover) {
        this.cameraMover = cameraMover;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.D)) {
            cameraMover.setMovingLeft(false);
        }

        if (keyEvent.getCode().equals(KeyCode.A)) {
            cameraMover.setMovingRight(false);
        }

        if (keyEvent.getCode().equals(KeyCode.S)) {
            cameraMover.setMovingDown(false);
        }

        if (keyEvent.getCode().equals(KeyCode.W)) {
            cameraMover.setMovingUp(false);
        }
    }
}
