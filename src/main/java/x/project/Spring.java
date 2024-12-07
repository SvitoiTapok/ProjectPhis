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
        double width = rightBox.getX() - getX();
        getView().setWidth(width);
    }

    @Override
    public void updateOnScreenPosition() {
        setX(leftBox.getX() + leftBox.getWidth());
        updateWidth();

        super.updateOnScreenPosition();
    }
}
