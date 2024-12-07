package x.project;

import javafx.scene.shape.Rectangle;
import lombok.Getter;

@Getter
public class Spring extends SceneObject {
    private final Box leftBox;
    private final Box rightBox;

    public Spring(Box leftBox, Box rightBox, Rectangle view) {
        super(view, leftBox.getX() + leftBox.getWidth(), 400+leftBox.getView().getHeight() / 3);
        this.leftBox = leftBox;
        this.rightBox = rightBox;

        double height = leftBox.getView().getHeight() / 3;

        updateWidth();
        view.setHeight(height);

        updateOnScreenPosition();
    }

    public void updateWidth(){
        double startX = leftBox.getX() + leftBox.getWidth();
        double width = rightBox.getX() - startX;
        getView().setWidth(width);
    }
}
