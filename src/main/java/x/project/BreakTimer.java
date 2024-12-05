package x.project;

import javafx.animation.AnimationTimer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BreakTimer extends AnimationTimer {
    private long lastTime = 0;
    private boolean stop = false;
    private final Scene scene;

    public BreakTimer(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void handle(long now) {
        if (!stop) {
            lastTime = lastTime == 0 ? now : lastTime;
            scene.doFrame((now - lastTime) / 1.0e9);
        } else {
            scene.doFrame(0.0);
        }

        lastTime = now;
    }
}

