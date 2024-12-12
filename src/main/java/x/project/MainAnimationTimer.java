package x.project;

import javafx.animation.AnimationTimer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MainAnimationTimer extends AnimationTimer {
    private long lastTime = 0;
    private boolean stop = false;
    private final Physics physics;
    private final List<DrawableObject> sceneObjects = new ArrayList<>();
    private final SceneController sceneController;

    public MainAnimationTimer(Physics physics, List<DrawableObject> additionalSceneObjects, SceneController sceneController) {
        this.physics = physics;
        this.sceneController = sceneController;
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

        sceneController.getTLabel().setText("t = %.2fс".formatted(physics.getTime()));
        sceneController.getEnergyLabel().setText("E = %.2fДж".formatted(physics.getTotalEnergy()));
        sceneController.getDeltaEnergyLabel().setText("dE = %.2fДж".formatted(physics.getStartEnergy() - physics.getTotalEnergy() - physics.getTotalEnergyLoss()));
    }
}

