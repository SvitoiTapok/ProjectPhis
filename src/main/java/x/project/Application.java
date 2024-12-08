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
        javafx.scene.Scene scene = new javafx.scene.Scene(fxmlLoader.load(), 3000, 3000);
        scene.getStylesheets().add(getResourcePath("/styles/styles.css"));
        SceneController sceneController = fxmlLoader.getController();
        Pane pane = sceneController.getPane();
        BorderPane borderPane = sceneController.getBorderPane();

        Physics physics = new Physics();
        ParametersChanger.PARAMETERS_CHANGER.setPhysics(physics);
        List<Box> boxes = physics.getBoxesList();

        Rectangle background = new Rectangle(0, 0, scene.getWidth(), scene.getHeight());

        SceneObject tableSurface = new SceneObject(new Rectangle(0, 0, scene.getWidth(), 100), 0, 400 + boxes.getFirst().getWidth());
        SceneObject tableBottom = new SceneObject(new Rectangle(0, 0, scene.getWidth(), scene.getHeight()), 0, tableSurface.getY() + tableSurface.getHeight());

        background.setFill(Color.rgb(166, 239, 255, 0.8));
        tableSurface.getView().setFill(Color.rgb(235, 115, 48, 1.0));
        tableBottom.getView().setFill(Color.rgb(199, 70, 31, 1.0));

        pane.getChildren().addLast(background);
        pane.getChildren().addLast(tableSurface.getView());
        pane.getChildren().addLast(tableBottom.getView());

        pane.getChildren().addAll(boxes.stream().map(Box::getView).toList());
        pane.getChildren().addAll(physics.getSpring().getView());

        tableSurface.setXUpdatable(false);
        tableBottom.setXUpdatable(false);
        StoppableTimer stoppableTimer = new StoppableTimer(physics, List.of(tableSurface, tableBottom));

        List<ObjectDescription> objectDescriptions = boxes.stream()
                .map(box ->
                        new ObjectDescription(pane, box, boxes, Mode.VELOCITY)
                ).toList();

        //организация движения камеры
        borderPane.requestFocus();
        CameraMover cameraMover = CameraMover.CAMERA_MOVER;
        borderPane.setOnKeyPressed(new PressHandler(cameraMover));
        borderPane.setOnKeyReleased(new ReleaseHandler(cameraMover));
        AnimationTimer viewTimer = new ViewTimer(objectDescriptions);
        viewTimer.start();

        Pane bottomPane = new Pane();
        borderPane.setBottom(bottomPane);
        bottomPane.setPrefHeight(120);

        Button stopButton = new Button("Остановить/\nпродолжить");
        stopButton.getStyleClass().add("controller");
        stopButton.setPrefWidth(400);
        stopButton.setPrefHeight(100);
        stopButton.setLayoutX(30);
        stopButton.setOnAction(event -> stoppableTimer.setStop(!stoppableTimer.isStop()));

        Button recreateButton = new Button("Восстановить");
        recreateButton.getStyleClass().add("controller");
        recreateButton.setPrefWidth(300);
        recreateButton.setPrefHeight(100);
        recreateButton.setLayoutX(460);
        recreateButton.setOnAction(event -> {
            physics.restart();
            stoppableTimer.setStop(true);
            CameraMover.CAMERA_MOVER.setCameraX(0.0);
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
            for (ObjectDescription objectDescription : objectDescriptions) {
                objectDescription.setMode(value);
            }
        });

        pane.getChildren().addAll(stopButton, recreateButton, comboBox);

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
