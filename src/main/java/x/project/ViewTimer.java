package x.project;

import javafx.animation.AnimationTimer;

import java.util.List;

public class ViewTimer extends AnimationTimer {
    private final List<ObjectDescription> objectDescriptions;

    public ViewTimer(List<ObjectDescription> objectDescriptions) {
        this.objectDescriptions = objectDescriptions;
    }

    @Override
    public void handle(long l) {
        CameraMover.CAMERA_MOVER.handleMove();

        for (ObjectDescription objectDescription : objectDescriptions) {
            objectDescription.hideAll();
            objectDescription.showAll();
        }
    }
}
