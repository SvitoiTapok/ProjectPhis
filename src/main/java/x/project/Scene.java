package x.project;

import lombok.Getter;
import lombok.Setter;

public class Scene {
    private static final double MU = 0.04;
    private static final double SPRING_K = 400;
    private static final double SCALE_FACTOR = 50.0;
    private static final double GRAVITY = 10.0;

    private final Box firstBox;
    private final Box secondBox;
    private final Box thirdBox;
    private final Spring spring;

    private final double defaultSpringLength;

    @Setter
    @Getter
    private boolean areBoxesJoined = false;

    @Getter
    private double totalEnergyLoss = 0;

    @Getter
    private double startEnergy = 0;

    public Scene(Box firstBox, Box secondBox, Box thirdBox, Spring spring) {
        this.firstBox = firstBox;
        this.secondBox = secondBox;
        this.thirdBox = thirdBox;
        this.spring = spring;

        defaultSpringLength = getSpringLength();
        startEnergy = getTotalKineticEnergy();
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

        System.out.println((startEnergy - getTotalEnergy() - getTotalEnergyLoss()));
    }

    public double getTotalKineticEnergy() {
        return firstBox.getKineticEnergy() + secondBox.getKineticEnergy() + thirdBox.getKineticEnergy();
    }

    public double getTotalPotentialEnergy() {
        double deformation = getSpringDeformation();
        return 0.5 * SPRING_K * deformation * deformation;
    }

    public double getTotalEnergy() {
        return getTotalKineticEnergy() + getTotalPotentialEnergy();
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
        double boxMass = box.getMass();

        if (boxMass == 0) {
            return;
        }

        double frictionForce = MU * GRAVITY * boxMass;
        double maxFrictionImpulse = frictionForce * deltaTime;

        double boxImpulse = box.getImpulse();
        double realFrictionImpulseChange = Math.min(Math.abs(maxFrictionImpulse), Math.abs(boxImpulse));
        double frictionImpulse = boxImpulse >= 0 ? -realFrictionImpulseChange : realFrictionImpulseChange;

        double energyBefore = box.getKineticEnergy();
        box.addImpulse(frictionImpulse);

        totalEnergyLoss += energyBefore - box.getKineticEnergy();
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
        double energyBefore = firstBox.getKineticEnergy() + secondBox.getKineticEnergy();

        firstBox.setMass(0.0);
        secondBox.setVelocity(impulse / mass);
        secondBox.setMass(mass);

        totalEnergyLoss += energyBefore - secondBox.getKineticEnergy();
    }

    private double getSpringDeformation() {
        return (defaultSpringLength - getSpringLength()) / SCALE_FACTOR;
    }

    private double getSpringLength() {
        return thirdBox.getX() - (secondBox.getX() + secondBox.getWidth());
    }
}