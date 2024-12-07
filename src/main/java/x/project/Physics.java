package x.project;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static x.project.Application.getResourcePath;

public class Physics {
    private static final double SCALE_FACTOR = 50.0;
    private static final double GRAVITY = 10.0;

    @Getter
    @Setter
    double firstBoxDefaultMass = 1.0;

    @Getter
    @Setter
    double secondBoxDefaultMass = 1.0;

    @Getter
    @Setter
    double thirdBoxDefaultMass = 2.0;

    @Getter
    @Setter
    double firstBoxDefaultVelocity = 5.0;

    @Getter
    @Setter
    double secondBoxDefaultVelocity = 0.0;

    @Getter
    @Setter
    double thirdBoxDefaultVelocity = 0.0;

    @Getter
    @Setter
    private double frictionCoefficient = 0.04;

    @Getter
    @Setter
    private double springConstant = 400;

    private final Box firstBox = createFirstBox();
    private final Box secondBox = createSecondBox();
    private final Box thirdBox = createThirdBox();

    @Getter
    private final Spring spring = createSpring(secondBox, thirdBox);

    private final double defaultSpringLength = getSpringLength();

    @Getter
    private double totalEnergyLoss = 0;

    @Getter
    private double startEnergy = getTotalKineticEnergy();

    private Box createFirstBox() {
        Box newBox = new Box(200, 100, firstBoxDefaultMass, firstBoxDefaultVelocity, 0, new Rectangle());
        newBox.getView().setFill(new ImagePattern(new Image(getResourcePath("/images/square_red.png"))));
        return newBox;
    }

    private Box createSecondBox() {
        Box newBox = new Box(600, 100, secondBoxDefaultMass, secondBoxDefaultVelocity, 0, new Rectangle());
        newBox.getView().setFill(new ImagePattern(new Image(getResourcePath("/images/square_green.png"))));
        return newBox;
    }

    private Box createThirdBox() {
        Box newBox = new Box(1000, 100, thirdBoxDefaultMass, thirdBoxDefaultVelocity, 0, new Rectangle());
        newBox.getView().setFill(new ImagePattern(new Image(getResourcePath("/images/square_purple.png"))));
        return newBox;
    }

    private Spring createSpring(Box leftBox, Box rightBox) {
        Spring newSpring = new Spring(leftBox, rightBox, new Rectangle());
        newSpring.getView().setFill(new ImagePattern(new Image(getResourcePath("/images/spring_horizontal.png"))));
        return newSpring;
    }

    public List<Box> getBoxesList() {
        return List.of(firstBox, secondBox, thirdBox);
    }

    private void reinitBox(Box box) {
        box.move(0.0, SCALE_FACTOR);
        box.setSpringForce(0.0);
        box.setFrictionForce(0.0);
        box.setAcceleration(0.0);
    }

    public void restart() {
        totalEnergyLoss = 0.0;
        firstBox.setJoined(false);

        firstBox.setX(200);
        secondBox.setX(600);
        thirdBox.setX(1000);

        reinitBox(firstBox);
        reinitBox(secondBox);
        reinitBox(thirdBox);

        firstBox.setMass(firstBoxDefaultMass);
        secondBox.setMass(secondBoxDefaultMass);
        thirdBox.setMass(thirdBoxDefaultMass);

        firstBox.setVelocity(firstBoxDefaultVelocity);
        secondBox.setVelocity(secondBoxDefaultVelocity);
        thirdBox.setVelocity(thirdBoxDefaultVelocity);

        firstBox.setPreviousVelocity(firstBoxDefaultVelocity);
        secondBox.setPreviousVelocity(secondBoxDefaultVelocity);
        thirdBox.setPreviousVelocity(thirdBoxDefaultVelocity);
    }

    public void hotReload() {
        if (firstBox.isJoined()) {
            secondBox.setMass(firstBoxDefaultMass + secondBoxDefaultMass);
            thirdBox.setMass(thirdBoxDefaultMass);
        } else {
            firstBox.setMass(firstBoxDefaultMass);
            secondBox.setMass(secondBoxDefaultMass);
            thirdBox.setMass(thirdBoxDefaultMass);
        }
    }

    public void doFrame(double deltaTime) {
        detectBoxesCollision();

        addSpringForce(deltaTime);
        addFrictionForce(deltaTime);

        if (firstBox.isJoined()) {
            firstBox.setAcceleration(secondBox.getAcceleration());
            firstBox.setVelocity(secondBox.getVelocity());
        }

        firstBox.move(deltaTime, SCALE_FACTOR);
        secondBox.move(deltaTime, SCALE_FACTOR);
        thirdBox.move(deltaTime, SCALE_FACTOR);
    }

    public double getTotalKineticEnergy() {
        return firstBox.getKineticEnergy() + secondBox.getKineticEnergy() + thirdBox.getKineticEnergy();
    }

    public double getTotalPotentialEnergy() {
        double deformation = getSpringDeformation();
        return 0.5 * springConstant * deformation * deformation;
    }

    public double getTotalEnergy() {
        return getTotalKineticEnergy() + getTotalPotentialEnergy();
    }

    private void addSpringForce(double deltaTime) {
        if (!firstBox.isJoined()) {
            return;
        }

        double springDeformation = getSpringDeformation();
        double springForce = springDeformation * springConstant;

        secondBox.setSpringForce(-springForce);
        thirdBox.setSpringForce(springForce);

        secondBox.addImpulse(-springForce * deltaTime);
        thirdBox.addImpulse(springForce * deltaTime);
    }

    private void addFrictionForce(double deltaTime) {
        if (!firstBox.isJoined()) {
            addFrictionForceForTheBox(firstBox, deltaTime);
        }

        addFrictionForceForTheBox(secondBox, deltaTime);
        addFrictionForceForTheBox(thirdBox, deltaTime);
    }

    private void addFrictionForceForTheBox(Box box, double deltaTime) {
        double boxMass = box.getMass();
        double frictionForce = frictionCoefficient * GRAVITY * boxMass;
        double maxFrictionImpulse = frictionForce * deltaTime;

        double boxImpulse = box.getImpulse();
        double realFrictionImpulseChange = Math.min(Math.abs(maxFrictionImpulse), Math.abs(boxImpulse));
        double frictionImpulse = boxImpulse >= 0 ? -realFrictionImpulseChange : realFrictionImpulseChange;

        double energyBefore = box.getKineticEnergy();
        box.addImpulse(frictionImpulse);
        box.setFrictionForce(frictionImpulse / deltaTime);

        totalEnergyLoss += energyBefore - box.getKineticEnergy();
    }

    private void detectBoxesCollision() {
        if (firstBox.isJoined()) {
            return;
        }

        double firstBoxRightCorner = firstBox.getX() + firstBox.getWidth();

        if (firstBoxRightCorner < secondBox.getX()) {
            return;
        }

        joinBoxes();
    }

    private void joinBoxes() {
        firstBox.setJoined(true);

        double impulse = firstBox.getImpulse() + secondBox.getImpulse();
        double mass = firstBox.getMass() + secondBox.getMass();
        double energyBefore = firstBox.getKineticEnergy() + secondBox.getKineticEnergy();

        firstBox.setMass(0.0);
        firstBox.setFrictionForce(0.0);

        secondBox.setVelocity(impulse / mass);
        secondBox.setMass(mass);

        totalEnergyLoss += energyBefore - secondBox.getKineticEnergy();
    }

    public double getSpringDeformation() {
        return defaultSpringLength - getSpringLength();
    }

    public double getSpringLength() {
        double secondBoxRightBorder = secondBox.getX() + secondBox.getWidth();
        return (thirdBox.getX() - secondBoxRightBorder) / SCALE_FACTOR;
    }
}
