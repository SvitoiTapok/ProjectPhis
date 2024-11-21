package x.project;

import java.util.ArrayList;

public class System {
    private final ArrayList<Box> component;
    private final static double mu = 0.2;
    private final static double k = 400;

    public System(ArrayList<Box> component) {
        this.component = component;
    }
    public void doFrame(long deltaTime){
        for(Box box: component){
            box.move(deltaTime);
        }
    }
}
