package x.project.handlers;

import lombok.Getter;
import lombok.Setter;
import x.project.Box;
import x.project.Physics;

import java.util.ArrayList;
import java.util.List;

public class ParametersChanger {
    public final static ParametersChanger PARAMETERS_CHANGER = new ParametersChanger();
    private ParametersChanger(){}
    @Getter
    @Setter
    private Physics physics;
    @Getter
    @Setter
    private List<Box> boxes;
    public void changeMu(double mu){
        physics.setFrictionCoefficient(mu);
    }
    public void changek(double k){
        physics.setSpringConstant(k);
    }
    public void changeM1(double m1){
        boxes.get(0).setMass(m1);
    }
    public void changeM2(double m2){
        boxes.get(1).setMass(m2);
    }
    public void changeM3(double m3){
        boxes.get(2).setMass(m3);
    }
}
