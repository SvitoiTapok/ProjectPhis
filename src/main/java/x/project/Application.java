package x.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
        GridPane gridPane = sceneController.getGridPane();

        Physics physics = new Physics();
        ParametersChanger.PARAMETERS_CHANGER.setPhysics(physics);
        List<Box> boxes = physics.getBoxesList();

        Rectangle background = new Rectangle(0, 0, scene.getWidth(), scene.getHeight());

        PhysicalObject tableSurface = new PhysicalObject(new Rectangle(0, 0, scene.getWidth(), 100), 0, 400 + boxes.getFirst().getWidth());
        PhysicalObject tableBottom = new PhysicalObject(new Rectangle(0, 0, scene.getWidth(), scene.getHeight()), 0, tableSurface.getY() + tableSurface.getHeight());

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

        List<ObjectDescription> objectDescriptions = new ArrayList<>();

        objectDescriptions.addAll(boxes.stream()
                .map(box ->
                        new ObjectDescription(pane, box, boxes, Mode.VELOCITY)
                ).toList());

        objectDescriptions.addLast(new ObjectDescription(pane, physics.getSpring(), boxes, Mode.FRICTION_FORCE));

        List<DrawableObject> sceneObjects = new ArrayList<>(List.of(tableSurface, tableBottom));
        sceneObjects.addAll(objectDescriptions);

        MainAnimationTimer mainAnimationTimer = new MainAnimationTimer(physics, sceneObjects, sceneController);

        //организация движения камеры
        borderPane.requestFocus();
        CameraMover cameraMover = CameraMover.CAMERA_MOVER;
        borderPane.setOnKeyPressed(new PressHandler(cameraMover));
        borderPane.setOnKeyReleased(new ReleaseHandler(cameraMover));

        Button stopButton = new Button("Остановить/\nпродолжить");
        stopButton.getStyleClass().add("controller");
        stopButton.setMinWidth(180);
        stopButton.setMinHeight(80);
        stopButton.setLayoutX(30);
        stopButton.setOnAction(event -> mainAnimationTimer.setStop(!mainAnimationTimer.isStop()));

        Button recreateButton = new Button("Сбросить");
        recreateButton.getStyleClass().add("controller");
        recreateButton.setMinWidth(180);
        recreateButton.setMinHeight(80);
        recreateButton.setLayoutX(290);
        recreateButton.setOnAction(event -> {
            physics.restart();
            mainAnimationTimer.setStop(true);
            CameraMover.CAMERA_MOVER.setCameraX(0.0);
        });

        ObservableList<Mode> modes = FXCollections.observableArrayList(Mode.VELOCITY, Mode.ACCELERATION, Mode.FRICTION_FORCE, Mode.SPRING_FORCE);
        ComboBox<Mode> comboBox = new ComboBox<>(modes);
        comboBox.getStyleClass().add("combo-box");
        comboBox.setValue(Mode.VELOCITY);
        comboBox.setPrefWidth(180);
        comboBox.setPrefHeight(80);
        comboBox.setLayoutX(550);
        comboBox.setOnAction(event -> {
            Mode value = comboBox.getValue();
            for (ObjectDescription objectDescription : objectDescriptions) {
                objectDescription.setMode(value);
            }
        });


        gridPane.add(stopButton, 2, 1);
        GridPane.setRowSpan(stopButton, 2);
        GridPane.setColumnSpan(stopButton, 2);
        GridPane.setHalignment(stopButton, HPos.CENTER); // Горизонтальное выравнивание
        GridPane.setValignment(stopButton, VPos.CENTER); // Вертикальное выравнивание
        gridPane.add(recreateButton, 4, 1);
        GridPane.setRowSpan(recreateButton, 2);
        GridPane.setColumnSpan(recreateButton, 2);
        GridPane.setHalignment(recreateButton, HPos.CENTER); // Горизонтальное выравнивание
        GridPane.setValignment(recreateButton, VPos.CENTER); // Вертикальное выравнивание
        gridPane.add(comboBox, 6, 1);
        GridPane.setRowSpan(comboBox, 2);
        GridPane.setColumnSpan(comboBox, 2);
        GridPane.setHalignment(comboBox, HPos.CENTER); // Горизонтальное выравнивание
        GridPane.setValignment(comboBox, VPos.CENTER); // Вертикальное выравнивание
        //pane.getChildren().addAll(stopButton, recreateButton, comboBox);
        stage.setTitle("Project");
        stage.setWidth(1280);
        stage.setHeight(768);
        stage.setMaximized(true);
        stage.getIcons().add(new Image(getResourcePath("/images/spring.png")));
        stage.setScene(scene);
        stage.show();
        mainAnimationTimer.start();
        mainAnimationTimer.setStop(true);
    }

    public static void main(String[] args) {
        launch();
    }
}
