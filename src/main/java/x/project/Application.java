package x.project;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Application extends javafx.application.Application {
    private long lastTime = 0;

    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        BorderPane borderPane = new BorderPane();
        Pane pane = new Pane();
        Scene scene = new Scene(borderPane, 1540, 1000);
        borderPane.setCenter(pane);

        Rectangle view1 = new Rectangle();
        Rectangle view2 = new Rectangle();
        Rectangle view3 = new Rectangle();

        Box box1 = new Box(200, 100, 200, -20, view1);
        Box box2 = new Box(600, 100, 0, 0, view2);
        Box box3 = new Box(1000, 100, 0, 0, view3);

        view1.setFill(new ImagePattern(new Image("images/square_red.png")));
        view2.setFill(new ImagePattern(new Image("images/square_green.png")));
        view3.setFill(new ImagePattern(new Image("images/square_purple.png")));

        Rectangle background = new Rectangle(0, 0, scene.getWidth(), scene.getHeight());

        Rectangle tableSurface = new Rectangle(0, 400 + box1.getSize(), scene.getWidth(), 100);
        Rectangle tableBottom = new Rectangle(0, tableSurface.getY() + tableSurface.getHeight(), scene.getWidth(), scene.getHeight());

        background.setFill(Color.rgb(166, 239, 255, 0.8));
        tableSurface.setFill(Color.rgb(235, 115, 48, 1.0));
        tableBottom.setFill(Color.rgb(199, 70, 31, 1.0));

        pane.getChildren().add(background);
        pane.getChildren().addAll(tableSurface, tableBottom);
        pane.getChildren().addAll(view1, view2, view3);

        ArrayList<Box> boxes = new ArrayList<>();
        boxes.add(box1);
        boxes.add(box2);
        boxes.add(box3);

        Physics physics = new Physics(boxes);


        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    physics.doFrame(0);
                    lastTime = now;
                    return;
                }
                physics.doFrame((now - lastTime) / 1.0e9);
                lastTime = now;
            }
        };
        animationTimer.start();

        stage.setTitle("Project");
        stage.setWidth(1280);
        stage.setHeight(768);
        stage.setMaximized(true);
        stage.getIcons().add(new Image("/images/spring.png"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}