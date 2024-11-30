package x.project;

import javafx.animation.AnimationTimer;

import java.util.ArrayList;

public class ViewTimer extends AnimationTimer {
    private ArrayList<Box> boxes;

    public ViewTimer(ArrayList<Box> boxes) {
        this.boxes = boxes;
    }

    @Override
    public void handle(long l) {
        for (Box box: boxes){
            CameraMover.CAMERA_MOVER.moveBox(box);
        }
    }
}
