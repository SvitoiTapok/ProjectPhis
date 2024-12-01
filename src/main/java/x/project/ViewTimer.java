package x.project;

import javafx.animation.AnimationTimer;

import java.util.ArrayList;

public class ViewTimer extends AnimationTimer {
    private final ArrayList<Box> boxes;
    private final ArrayList<ShowDescription> showDescriptions;
    private final BreakTimer breakTimer;

    public ViewTimer(ArrayList<Box> boxes, ArrayList<ShowDescription> showDescriptions, BreakTimer breakTimer) {
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
