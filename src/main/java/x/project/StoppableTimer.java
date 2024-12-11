package x.project;

import javafx.animation.AnimationTimer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StoppableTimer extends AnimationTimer {
    private long lastTime = 0;
    private boolean stop = false;
    private final Physics physics;
    private final List<DrawableObject> sceneObjects = new ArrayList<>();

    public StoppableTimer(Physics physics, List<DrawableObject> additionalSceneObjects) {
        this.physics = physics;

        sceneObjects.addAll(physics.getBoxesList());
        sceneObjects.addAll(additionalSceneObjects);
        sceneObjects.add(physics.getSpring());
    }

    @Override
    public void handle(long now) {
        CameraMover.CAMERA_MOVER.handleMove();

        if (!stop) {
            lastTime = lastTime == 0 ? now : lastTime;
            physics.doFrame((now - lastTime) / 1.0e9);
        }

        sceneObjects.forEach(DrawableObject::draw);
        lastTime = now;
    }
}

