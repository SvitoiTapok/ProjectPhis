package x.project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import x.project.handlers.ParametersChanger;

@Getter
@Setter
public class SceneController {
    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField kTextField;

    @FXML
    private TextField m1TextField;

    @FXML
    private TextField m2TextField;

    @FXML
    private TextField m3TextField;

    @FXML
    private TextField muTextField;

    @FXML
    private TextField vTextField;

    @FXML
    private Label changeMessage;

    @FXML
    private Pane pane;

    @FXML
    private GridPane gridPane;

    private double mu = 0.04, k, m1, m2, m3;

    @FXML
    void myChange() {
        boolean f1 = true, f2 = true, f3 = true, f4 = true, f5 = true, f6 = true;

        try {
            ParametersChanger.PARAMETERS_CHANGER.changeFrictionCoefficient(Double.parseDouble(muTextField.getText()));
            muTextField.setPromptText(muTextField.getText());
        } catch (Exception ignored) {
            f1 = false;
        }

        try {
            ParametersChanger.PARAMETERS_CHANGER.changeSpringCoefficient(Double.parseDouble(kTextField.getText()));
            kTextField.setPromptText(kTextField.getText());
        } catch (Exception ignored) {
            f2 = false;
        }

        try {
            ParametersChanger.PARAMETERS_CHANGER.changeM1(Double.parseDouble(m1TextField.getText()));
            m1TextField.setPromptText(m1TextField.getText());
        } catch (Exception ignored) {
            f3 = false;
        }

        try {
            ParametersChanger.PARAMETERS_CHANGER.changeM2(Double.parseDouble(m2TextField.getText()));
            m2TextField.setPromptText(m2TextField.getText());
        } catch (Exception ignored) {
            f4 = false;
        }

        try {
            ParametersChanger.PARAMETERS_CHANGER.changeM3(Double.parseDouble(m3TextField.getText()));
            m3TextField.setPromptText(m3TextField.getText());
        } catch (Exception e) {
            f5 = false;
        }
        try {
            ParametersChanger.PARAMETERS_CHANGER.changeV1(Double.parseDouble(vTextField.getText()));
            vTextField.setPromptText(vTextField.getText());
        } catch (Exception e) {
            f6 = false;
        }

        if (!(f1 || f2 || f3 || f4 || f5)) {
            changeMessage.setText("Не удалось изменить величины");
            return;
        } else {
            ParametersChanger.PARAMETERS_CHANGER.getPhysics().hotReload();
        }

        String output = "";
        output += f1 ? "mu " : "";
        output += f2 ? "k " : "";
        output += f3 ? "m1 " : "";
        output += f4 ? "m2 " : "";
        output += f5 ? "m3 " : "";
        changeMessage.setText(output + "успешно изменен(ы)!");
    }

}
