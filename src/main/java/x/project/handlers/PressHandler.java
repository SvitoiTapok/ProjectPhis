package x.project.handlers;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import x.project.CameraMover;

public class PressHandler implements EventHandler<KeyEvent> {
    private final CameraMover cameraMover;

    public PressHandler(CameraMover cameraMover) {
        this.cameraMover = cameraMover;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.D)){
            cameraMover.setMovingLeft(true);
        }
        if(keyEvent.getCode().equals(KeyCode.A)){
            cameraMover.setMovingRight(true);
        }
    }
}
