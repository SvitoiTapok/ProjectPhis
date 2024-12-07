package x.project;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import x.project.handlers.ParametersChanger;
import x.project.handlers.PressHandler;
import x.project.handlers.ReleaseHandler;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Application extends javafx.application.Application {
    public static String getResourcePath(String path) {
        return Objects.requireNonNull(Application.class.getResource(path)).toExternalForm();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Scene.fxml"));
        javafx.scene.Scene scene = new javafx.scene.Scene(fxmlLoader.load(), 3000, 1000);
        scene.getStylesheets().add(getResourcePath("/styles/styles.css"));
        SceneController sceneController = fxmlLoader.getController();
        Pane pane = sceneController.getPane();
        BorderPane borderPane = sceneController.getBorderPane();

        Physics physics = new Physics();
        ParametersChanger.PARAMETERS_CHANGER.setPhysics(physics);
        List<Box> boxes = physics.getBoxesList();

        Rectangle background = new Rectangle(0, 0, scene.getWidth(), scene.getHeight());
        Rectangle tableSurface = new Rectangle(0, 400 + boxes.getFirst().getWidth(), scene.getWidth(), 100);
        Rectangle tableBottom = new Rectangle(0, tableSurface.getY() + tableSurface.getHeight(), scene.getWidth(), scene.getHeight());

        background.setFill(Color.rgb(166, 239, 255, 0.8));
        tableSurface.setFill(Color.rgb(235, 115, 48, 1.0));
        tableBottom.setFill(Color.rgb(199, 70, 31, 1.0));

        pane.getChildren().addLast(background);
        pane.getChildren().addLast(tableSurface);
        pane.getChildren().addLast(tableBottom);

        pane.getChildren().addAll(boxes.stream().map(Box::getView).toList());
        pane.getChildren().addAll(physics.getSpring().getView());

        StoppableTimer stoppableTimer = new StoppableTimer(physics);

        List<ShowDescription> showDescriptions = boxes.stream()
                .map(box ->
                        new ShowDescription(pane, box, Mode.VELOCITY)
                ).toList();

        //организация движения камеры
        borderPane.requestFocus();
        CameraMover cameraMover = CameraMover.CAMERA_MOVER;
        borderPane.setOnKeyPressed(new PressHandler(cameraMover));
        borderPane.setOnKeyReleased(new ReleaseHandler(cameraMover));
        AnimationTimer viewTimer = new ViewTimer(physics.getBoxesList(), showDescriptions);
        viewTimer.start();

        Pane bottomPane = new Pane();
        borderPane.setBottom(bottomPane);
        bottomPane.setPrefHeight(120);

        Button breakButton = new Button("break/continue");
        breakButton.getStyleClass().add("controller");
        breakButton.setPrefWidth(400);
        breakButton.setPrefHeight(100);
        breakButton.setLayoutX(30);
        breakButton.setOnAction(event -> stoppableTimer.setStop(!stoppableTimer.isStop()));

        Button recreateButton = new Button("restart");
        recreateButton.getStyleClass().add("controller");
        recreateButton.setPrefWidth(300);
        recreateButton.setPrefHeight(100);
        recreateButton.setLayoutX(460);
        recreateButton.setOnAction(event -> {
            physics.restart();
            stoppableTimer.setStop(true);
        });

        ObservableList<Mode> modes = FXCollections.observableArrayList(Mode.VELOCITY, Mode.ACCELERATION, Mode.FRICTION_FORCE, Mode.SPRING_FORCE);
        ComboBox<Mode> comboBox = new ComboBox<>(modes);
        comboBox.getStyleClass().add("combo-box");
        comboBox.setValue(Mode.VELOCITY);
        comboBox.setPrefWidth(300);
        comboBox.setPrefHeight(100);
        comboBox.setLayoutX(790);
        comboBox.setOnAction(event -> {
            Mode value = comboBox.getValue();
            for (ShowDescription showDescription : showDescriptions) {
                showDescription.setMode(value);
            }
        });

        pane.getChildren().addAll(breakButton, recreateButton, comboBox);

        stage.setTitle("Project");
        stage.setWidth(1280);
        stage.setHeight(768);
        stage.setMaximized(true);
        stage.getIcons().add(new Image(getResourcePath("/images/spring.png")));
        stage.setScene(scene);
        stage.show();
        stoppableTimer.start();
        stoppableTimer.setStop(true);
    }

    public static void main(String[] args) {
        launch();
    }
}
