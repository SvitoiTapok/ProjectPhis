package x.project;

import javafx.animation.AnimationTimer;

import java.util.List;

public class ViewTimer extends AnimationTimer {
    private final List<Box> boxes;
    private final List<ShowDescription> showDescriptions;
    private final BreakTimer breakTimer;

    public ViewTimer(List<Box> boxes, List<ShowDescription> showDescriptions, BreakTimer breakTimer) {
        this.boxes = boxes;
        this.showDescriptions = showDescriptions;
        this.breakTimer = breakTimer;
    }

    @Override
    public void handle(long l) {
        for (Box box : boxes) {
            CameraMover.CAMERA_MOVER.moveBox(box);
        }

        if (breakTimer.isStop()) {
            for (ShowDescription showDescription : showDescriptions) {
                showDescription.hideAll();
                showDescription.showAll();
            }
        }
    }
}
