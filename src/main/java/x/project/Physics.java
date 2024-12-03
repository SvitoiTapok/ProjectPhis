package x.project;

import lombok.Getter;
import lombok.Setter;

public class Physics {
    private static final double MU = 0.03;
    private static final double SPRING_K = 400;
    private static final double SCALE_FACTOR = 50.0;
    private static final double GRAVITY = 10.0;

    private final Box firstBox;
    private final Box secondBox;
    private final Box thirdBox;
    private final Spring spring;

    @Setter
    @Getter
    private boolean areBoxesJoined = false;

    private final double defaultSpringLength;

    public Physics(Box firstBox, Box secondBox, Box thirdBox, Spring spring) {
        this.firstBox = firstBox;
        this.secondBox = secondBox;
        this.thirdBox = thirdBox;
        this.spring = spring;

        defaultSpringLength = thirdBox.getX() - (secondBox.getX() + secondBox.getWidth());
    }

    public void doFrame(double deltaTime) {
        detectBoxesCollision();
        addSpringForce(deltaTime);
        addFrictionForce(deltaTime);

        if (areBoxesJoined) {
            firstBox.setAcceleration(secondBox.getAcceleration());
            firstBox.setVelocity(secondBox.getVelocity());
        }

        firstBox.move(deltaTime, SCALE_FACTOR);
        secondBox.move(deltaTime, SCALE_FACTOR);
        thirdBox.move(deltaTime, SCALE_FACTOR);
        spring.move();
    }

    private void addSpringForce(double deltaTime) {
        if (!areBoxesJoined) {
            return;
        }

        double springDeformation = getSpringDeformation();
        double springForce = springDeformation * SPRING_K;

        secondBox.addImpulse(-springForce * deltaTime);
        thirdBox.addImpulse(springForce * deltaTime);
    }

    private void addFrictionForce(double deltaTime) {
        addFrictionForceForTheBox(firstBox, deltaTime);
        addFrictionForceForTheBox(secondBox, deltaTime);
        addFrictionForceForTheBox(thirdBox, deltaTime);
    }

    private void addFrictionForceForTheBox(Box box, double deltaTime) {
        double frictionForce = MU * GRAVITY * box.getMass();
        double maxFrictionImpulse = frictionForce * deltaTime;

        double boxImpulse = box.getImpulse();
        double realFrictionImpulseChange = Math.min(Math.abs(maxFrictionImpulse), Math.abs(boxImpulse));
        double frictionImpulse = boxImpulse > 0 ? -realFrictionImpulseChange : realFrictionImpulseChange;

        box.addImpulse(frictionImpulse);
    }

    private void detectBoxesCollision() {
        if (areBoxesJoined) {
            return;
        }

        double firstBoxRightCorner = firstBox.getX() + firstBox.getWidth();

        if (firstBoxRightCorner < secondBox.getX()) {
            return;
        }

        joinBoxes();
    }

    private void joinBoxes() {
        areBoxesJoined = true;

        double impulse = firstBox.getImpulse() + secondBox.getImpulse();
        double mass = firstBox.getMass() + secondBox.getMass();

        secondBox.setVelocity(impulse / mass);
        secondBox.setMass(mass);
    }

    private double getSpringDeformation() {
        double currentSpringLength = thirdBox.getX() - (secondBox.getX() + secondBox.getWidth());
        return (defaultSpringLength - currentSpringLength) / SCALE_FACTOR;
    }
}
