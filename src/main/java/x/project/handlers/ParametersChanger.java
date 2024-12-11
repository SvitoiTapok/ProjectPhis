package x.project.handlers;

import lombok.Getter;
import lombok.Setter;
import x.project.Physics;

@Getter
@Setter
public class ParametersChanger {
    public static final ParametersChanger PARAMETERS_CHANGER = new ParametersChanger();

    private ParametersChanger() {
    }

    private Physics physics;

    public void changeFrictionCoefficient(double mu) {
        physics.setFrictionCoefficient(mu);
    }

    public void changeSpringCoefficient(double k) {
        physics.setSpringConstant(k);
    }

    public void changeM1(double m1) {
        physics.setFirstBoxDefaultMass(m1);
    }

    public void changeM2(double m2) {
        physics.setSecondBoxDefaultMass(m2);
    }

    public void changeM3(double m3) {
        physics.setThirdBoxDefaultMass(m3);
    }

    public void changeV1(double v) {
        physics.setFirstBoxDefaultVelocity(v);
    }
}
