package x.project;

import javafx.animation.AnimationTimer;

import java.util.List;

public class ViewTimer extends AnimationTimer {
    private final List<ShowDescription> showDescriptions;

    public ViewTimer(List<ShowDescription> showDescriptions) {
        this.showDescriptions = showDescriptions;
    }

    @Override
    public void handle(long l) {
        CameraMover.CAMERA_MOVER.handleMove();

        for (ShowDescription showDescription : showDescriptions) {
            showDescription.hideAll();
            showDescription.showAll();
        }
    }
}
