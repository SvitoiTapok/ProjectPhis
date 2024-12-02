package x.project;

import java.util.List;

public class Physics {
    private final static double mu = 0.2;
    private final static double k = 400;

    private final List<Box> component;
    private boolean areBoxesJoined = false;

    public Physics(List<Box> component) {
        this.component = component;
    }

    public void doFrame(double deltaTime) {
        Box firstBox = this.component.get(0);
        Box secondBox = this.component.get(1);
        Box thirdBox = this.component.get(2);

        detectBoxesCollision(firstBox, secondBox);

        for (Box box : component) {
            box.move(deltaTime);
            //CameraMover.CAMERA_MOVER.moveBox(box);
        }
    }

    private void detectBoxesCollision(Box firstBox, Box secondBox) {
        if (areBoxesJoined) {
            return;
        }

        double firstBoxRightCorner = firstBox.getX() + firstBox.getSize();

        if (firstBoxRightCorner >= secondBox.getX()) {
            areBoxesJoined = true;
            firstBox.setVelocity(0.0);
            firstBox.setAcceleration(0.0);
        }
    }
}
