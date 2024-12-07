package x.project;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import x.project.handlers.ParametersChanger;
import x.project.handlers.PressHandler;
import x.project.handlers.ReleaseHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Application extends javafx.application.Application {
    private String getResourcePath(String path) {
        return Objects.requireNonNull(getClass().getResource(path)).toExternalForm();
    }

    private Box createFirstBox() {
        Box newBox = new Box(200, 100, 8.0, 5, 0, new Rectangle());
        newBox.getView().setFill(new ImagePattern(new Image(getResourcePath("/images/square_red.png"))));
        return newBox;
    }

    private Box createSecondBox() {
        Box newBox = new Box(600, 100, 8.0, 0, 0, new Rectangle());
        newBox.getView().setFill(new ImagePattern(new Image(getResourcePath("/images/square_green.png"))));
        return newBox;
    }

    private Box createThirdBox() {
        Box newBox = new Box(1000, 100, 16.0, 0, 0, new Rectangle());
        newBox.getView().setFill(new ImagePattern(new Image(getResourcePath("/images/square_purple.png"))));
        return newBox;
    }

    private Spring createSpring(Box leftBox, Box rightBox) {
        Spring newSpring = new Spring(leftBox, rightBox, new Rectangle());
        newSpring.getView().setFill(new ImagePattern(new Image(getResourcePath("/images/spring_horizontal.png"))));
        return newSpring;
    }

    @Override
    public void start(Stage stage) throws IOException {
        //BorderPane borderPane = new BorderPane();
        //Pane pane = new Pane();
        //javafx.scene.Scene scene = new javafx.scene.Scene(borderPane, 1540, 1000);
        // borderPane.setCenter(pane);
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Scene.fxml"));
        javafx.scene.Scene scene = new javafx.scene.Scene(fxmlLoader.load(), 1540, 1000);
        scene.getStylesheets().add(getResourcePath("/styles/styles.css"));
        SceneController sceneController = fxmlLoader.getController();
        Pane pane = sceneController.getPane();
        BorderPane borderPane = sceneController.getBorderPane();


        Box box1 = createFirstBox();
        Box box2 = createSecondBox();
        Box box3 = createThirdBox();
        Spring spring = createSpring(box2, box3);



        Rectangle background = new Rectangle(0, 0, scene.getWidth(), scene.getHeight());
        Rectangle tableSurface = new Rectangle(0, 400 + box1.getWidth(), scene.getWidth(), 100);
        Rectangle tableBottom = new Rectangle(0, tableSurface.getY() + tableSurface.getHeight(), scene.getWidth(), scene.getHeight());

        background.setFill(Color.rgb(166, 239, 255, 0.8));
        tableSurface.setFill(Color.rgb(235, 115, 48, 1.0));
        tableBottom.setFill(Color.rgb(199, 70, 31, 1.0));

        pane.getChildren().add(0, background);
        pane.getChildren().add(1, tableSurface);
        pane.getChildren().add(2, tableBottom);
        //sceneController.getGrid().toFront();
        //pane.getChildren().addAll(tableSurface, tableBottom);
        pane.getChildren().addAll(box1.getView(), box2.getView(), box3.getView());
        pane.getChildren().addAll(spring.getView());

        List<Box> boxes = new ArrayList<>();
        boxes.add(box1);
        boxes.add(box2);
        boxes.add(box3);


        Physics physics = new Physics(box1, box2, box3, spring);

        ParametersChanger.PARAMETERS_CHANGER.setBoxes(boxes);
        ParametersChanger.PARAMETERS_CHANGER.setPhysics(physics);

        BreakTimer breakTimer = new BreakTimer(physics);
        ShowDescription showDescriptionBox1 = new ShowDescription(pane, box1);
        ShowDescription showDescriptionBox2 = new ShowDescription(pane, box2);
        ShowDescription showDescriptionBox3 = new ShowDescription(pane, box3);

        List<ShowDescription> showDescriptions = new ArrayList<>();
        showDescriptions.add(showDescriptionBox1);
        showDescriptions.add(showDescriptionBox2);
        showDescriptions.add(showDescriptionBox3);

        //организация движения камеры
        borderPane.requestFocus();
        CameraMover cameraMover = CameraMover.CAMERA_MOVER;
        borderPane.setOnKeyPressed(new PressHandler(cameraMover));
        borderPane.setOnKeyReleased(new ReleaseHandler(cameraMover));
        AnimationTimer viewTimer = new ViewTimer(boxes, showDescriptions);
        viewTimer.start();

        Pane bottomPane = new Pane();
        //bottomPane.getChildren().add(sceneController.getGrid());
        borderPane.setBottom(bottomPane);
        //borderPane.getBottom().toFront();
        bottomPane.setPrefHeight(120);

        Button breakButton = new Button("break/continue");
        breakButton.getStyleClass().add("controller");
        breakButton.setPrefWidth(400);
        breakButton.setPrefHeight(100);
        breakButton.setLayoutX(30);
        breakButton.setOnAction(event -> breakTimer.setStop(!breakTimer.isStop()));

        Button recreateButton = new Button("restart");
        recreateButton.getStyleClass().add("controller");
        recreateButton.setPrefWidth(300);
        recreateButton.setPrefHeight(100);
        recreateButton.setLayoutX(460);
        recreateButton.setOnAction(event -> {
            Box box1New = createFirstBox();
            Box box2New = createSecondBox();
            Box box3New = createThirdBox();

            box1.setX(box1New.getX());
            box2.setX(box2New.getX());
            box3.setX(box3New.getX());

            box1.setVelocity(box1New.getVelocity());
            box2.setVelocity(box2New.getVelocity());
            box3.setVelocity(box3New.getVelocity());

            box1.setAcceleration(box1New.getAcceleration());
            box2.setAcceleration(box2New.getAcceleration());
            box3.setAcceleration(box3New.getAcceleration());

            box1.setMass(box1New.getMass());
            box2.setMass(box2New.getMass());
            box3.setMass(box3New.getMass());

            physics.restart();
            breakTimer.setStop(true);
        });

        bottomPane.getChildren().addAll(breakButton, recreateButton);

        stage.setTitle("Project");
        //stage.setWidth(1280);
        //stage.setHeight(768);
        //stage.setMaximized(true);
        stage.getIcons().add(new Image(getResourcePath("/images/spring.png")));
        stage.setScene(scene);
        stage.show();
        breakTimer.start();
        breakTimer.setStop(true);
    }

    public static void main(String[] args) {
        launch();
    }
}
