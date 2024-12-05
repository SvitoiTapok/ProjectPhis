package x.project;

import javafx.animation.AnimationTimer;

import java.util.List;

public class ViewTimer extends AnimationTimer {
    private final List<Box> boxes;
    private final List<ShowDescription> showDescriptions;

    public ViewTimer(List<Box> boxes, List<ShowDescription> showDescriptions) {
        this.boxes = boxes;
        this.showDescriptions = showDescriptions;
    }

    @Override
    public void handle(long l) {
        for (Box box : boxes) {
            CameraMover.CAMERA_MOVER.moveBox(box);
        }

        for (ShowDescription showDescription : showDescriptions) {
            showDescription.hideAll();
            showDescription.showAll();
        }
    }
}
