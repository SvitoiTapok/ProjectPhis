package x.project;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CameraMover{
    public static final CameraMover CAMERA_MOVER = new CameraMover();
    private boolean isDPressed = false;
    private boolean isAPressed = false;
    public static final double OFFSET = 5.0;

    private CameraMover(){
        System.out.println("nice1");
    }

    public boolean isDPressed() {
        return isDPressed;
    }

    public boolean isAPressed() {
        return isAPressed;
    }

    public void setDPressed(boolean DPressed) {
        isDPressed = DPressed;
    }

    public void setAPressed(boolean APressed) {
        isAPressed = APressed;
    }

    public void moveBox(Box box){
        //System.out.println(isAPressed);
        if(isAPressed){
            box.setX(box.getX()+OFFSET);
            System.out.println("nice");
        }
        if(isDPressed){
            box.setX(box.getX()-OFFSET);
        }
    }
}
