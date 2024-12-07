package x.project;

import javafx.scene.shape.Rectangle;
import lombok.Getter;

@Getter
public class Spring extends SceneObject {
    private final Box leftBox;
    private final Box rightBox;

    public Spring(Box leftBox, Box rightBox, Rectangle view) {
        super(view, leftBox.getX() + leftBox.getWidth());
        this.leftBox = leftBox;
        this.rightBox = rightBox;

        double height = leftBox.getView().getHeight() / 3;

        view.setY(400 + height);
        view.setWidth(rightBox.getX() - (leftBox.getX() + leftBox.getWidth()));
        view.setHeight(height);

        updateOnScreenPosition();
    }

    @Override
    public void updateOnScreenPosition(){
        double startX = leftBox.getX() + leftBox.getWidth();
        double width = rightBox.getX() - startX;

        getView().setWidth(width);
        setX(startX);
    }
}
