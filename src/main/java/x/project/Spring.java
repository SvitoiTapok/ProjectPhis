package x.project;

import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Spring {
    private final Box leftBox;
    private final Box rightBox;
    private final Rectangle view;

    public Spring(Box leftBox, Box rightBox, Rectangle view) {
        this.leftBox = leftBox;
        this.rightBox = rightBox;
        double startX = leftBox.getX() + leftBox.getWidth();
        double height = leftBox.getView().getHeight() / 3;
        view.setX(startX);
        view.setY(400 + height);
        view.setWidth(rightBox.getX() - startX);
        view.setHeight(height);
        this.view = view;
    }

    public void move() {
        double startX = leftBox.getX() + leftBox.getWidth();
        double height = leftBox.getView().getHeight() / 3;
        view.setX(startX);
        view.setY(400 + height);
        view.setWidth(rightBox.getX() - startX);
        view.setHeight(height);
    }
}
