package x.project;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import x.project.handlers.PressHandler;
import x.project.handlers.ReleaseHandler;

import java.io.IOException;
import java.util.ArrayList;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        BorderPane borderPane = new BorderPane();
        Pane pane = new Pane();
        Scene scene = new Scene(borderPane, 1540, 1000);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
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
        BreakTimer animationTimer = new BreakTimer(physics);
        ShowDescription showDescriptionBox1 = new ShowDescription(pane, box1);
        ShowDescription showDescriptionBox2 = new ShowDescription(pane, box2);
        ShowDescription showDescriptionBox3 = new ShowDescription(pane, box3);

        //организация движения камеры
        borderPane.requestFocus();
        CameraMover cameraMover = CameraMover.CAMERA_MOVER;
        borderPane.setOnKeyPressed(new PressHandler(cameraMover));
        borderPane.setOnKeyReleased(new ReleaseHandler(cameraMover));
        AnimationTimer ViewTimer= new ViewTimer(boxes);
        ViewTimer.start();

        Pane bottomPane = new Pane();
        borderPane.setBottom(bottomPane);
        bottomPane.setPrefHeight(120);

        Button breakButton = new Button("break");
        breakButton.setPrefWidth(300);
        breakButton.setPrefHeight(100);
        breakButton.setLayoutX(30);
        breakButton.setOnAction(event -> {
            animationTimer.setStop(true);
            showDescriptionBox1.showAll();
            showDescriptionBox2.showAll();
            showDescriptionBox3.showAll();
        });

        Button continueButton = new Button("continue");
        continueButton.setPrefWidth(300);
        continueButton.setPrefHeight(100);
        continueButton.setLayoutX(360);
        continueButton.setOnAction(event -> {
            animationTimer.setStop(false);
            showDescriptionBox1.hideAll();
            showDescriptionBox2.hideAll();
            showDescriptionBox3.hideAll();
        });

        bottomPane.getChildren().addAll(breakButton, continueButton);

        stage.setTitle("Project");
        stage.setWidth(1280);
        stage.setHeight(768);
        stage.setMaximized(true);
        stage.getIcons().add(new Image("/images/spring.png"));
        stage.setScene(scene);
        stage.show();
        animationTimer.start();
    }

    public static void main(String[] args) {
        launch();
    }

}