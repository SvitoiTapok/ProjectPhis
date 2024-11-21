package x.project;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.Timestamp;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;

public class Application extends javafx.application.Application {
    private long lastTime=0;
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        BorderPane borderPane = new BorderPane();
        Pane pane = new Pane();
        Scene scene = new Scene(borderPane, 1500, 1000);
        borderPane.setCenter(pane);

        Rectangle view1 = new Rectangle();
        Rectangle view2 = new Rectangle();
        Rectangle view3 = new Rectangle();
        Box box1 = new Box(200, 100, 200, -20, view1);
        Box box2 = new Box(600, 100, 0, 0, view2);
        Box box3 = new Box(1000, 100, 0, 0, view3);

        pane.getChildren().add(view1);
        pane.getChildren().add(view2);
        pane.getChildren().add(view3);

        ArrayList<Box> boxes = new ArrayList<>();
        boxes.add(box1); boxes.add(box2); boxes.add(box3);

        System system = new System(boxes);


        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(lastTime==0){
                    system.doFrame(0);
                    lastTime = now;
                    return;
                }
                system.doFrame(now-lastTime);
                lastTime = now;
            }
        };
        animationTimer.start();

        stage.setTitle("Project");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}