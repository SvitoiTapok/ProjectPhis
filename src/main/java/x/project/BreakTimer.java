package x.project;

import javafx.animation.AnimationTimer;

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
            if (lastTime == 0) {
                physics.doFrame(0);
                lastTime = now;
                return;
            }
            physics.doFrame((now - lastTime) / 1.0e9);
        }
        lastTime = now;
        physics.doFrame(0);
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public Physics getPhysics() {
        return physics;
    }
}

