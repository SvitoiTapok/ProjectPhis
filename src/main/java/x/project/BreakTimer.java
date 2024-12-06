package x.project;

import javafx.animation.AnimationTimer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BreakTimer extends AnimationTimer {
    private long lastTime = 0;
    private boolean stop = false;
    private final Physics physics;

    public BreakTimer(Physics physics) {
        this.physics = physics;
    }

    @Override
    public void handle(long now) {
        if (!stop) {
            lastTime = lastTime == 0 ? now : lastTime;
            physics.doFrame((now - lastTime) / 1.0e9);
        } else {
            physics.doFrame(0.0);
        }

        lastTime = now;
    }
}

