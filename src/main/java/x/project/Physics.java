package x.project;

public class Physics {
    private static final double MU = 0.2;
    private static final double SPRING_K = 400;
    private static final double SCALE_FACTOR = 50.0;

    private final Box firstBox;
    private final Box secondBox;
    private final Box thirdBox;
    private boolean areBoxesJoined = false;
    private final double defaultSpringLength;

    public Physics(Box firstBox, Box secondBox, Box thirdBox) {
        this.firstBox = firstBox;
        this.secondBox = secondBox;
        this.thirdBox = thirdBox;

        defaultSpringLength = thirdBox.getX() - (secondBox.getX() + secondBox.getWidth());
    }

    public void doFrame(double deltaTime) {
        detectBoxesCollision();
        addSpringForce();

        if (areBoxesJoined) {
            firstBox.setAcceleration(secondBox.getAcceleration());
            firstBox.setVelocity(secondBox.getVelocity());
        }

        firstBox.move(deltaTime, SCALE_FACTOR);
        secondBox.move(deltaTime, SCALE_FACTOR);
        thirdBox.move(deltaTime, SCALE_FACTOR);
    }

    private void addSpringForce() {
        if (!areBoxesJoined) {
            return;
        }

        double springDeformation = getSpringDeformation();
        double springForce = springDeformation * SPRING_K;

        secondBox.setAcceleration(0.0);
        thirdBox.setAcceleration(0.0);

        secondBox.addHorizontalForce(-springForce);
        thirdBox.addHorizontalForce(springForce);
    }

    private void detectBoxesCollision() {
        if (areBoxesJoined) {
            return;
        }

        double firstBoxRightCorner = firstBox.getX() + firstBox.getWidth();

        if (firstBoxRightCorner < secondBox.getX()) {
            return;
        }

        areBoxesJoined = true;

        secondBox.setVelocity((
                firstBox.getImpulse() + secondBox.getImpulse())
                / (secondBox.getMass() + firstBox.getMass())
        );

        secondBox.setMass(secondBox.getMass() + firstBox.getMass());

        firstBox.setVelocity(0.0);
        firstBox.setAcceleration(0.0);
    }

    private double getSpringDeformation() {
        double currentSpringLength = thirdBox.getX() - (secondBox.getX() + secondBox.getWidth());
        return (defaultSpringLength - currentSpringLength) / SCALE_FACTOR;
    }
}
