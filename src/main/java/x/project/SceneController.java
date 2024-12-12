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

    @FXML
    private Label tLabel;


    @FXML
    private Label deltaEnergyLabel;

    @FXML
    private Label energyLabel;

    private double mu = 0.04, k, m1, m2, m3;

    @FXML
    void myChange() {
        boolean f1 = true, f2 = true, f3 = true, f4 = true, f5 = true, f6 = true;

        try {
            double value = Double.parseDouble(muTextField.getText());

            if (value < 0) {
                throw new Exception();
            }

            ParametersChanger.PARAMETERS_CHANGER.changeFrictionCoefficient(value);
            muTextField.setPromptText(muTextField.getText());
        } catch (Exception ignored) {
            f1 = false;
        }

        try {
            double value = Double.parseDouble(kTextField.getText());

            if (value <= 0) {
                throw new Exception();
            }

            ParametersChanger.PARAMETERS_CHANGER.changeSpringCoefficient(value);
            kTextField.setPromptText(kTextField.getText());
        } catch (Exception ignored) {
            f2 = false;
        }

        try {
            double value = Double.parseDouble(m1TextField.getText());

            if (value <= 0) {
                throw new Exception();
            }

            ParametersChanger.PARAMETERS_CHANGER.changeM1(value);
            m1TextField.setPromptText(m1TextField.getText());
        } catch (Exception ignored) {
            f3 = false;
        }

        try {
            double value = Double.parseDouble(m2TextField.getText());

            if (value <= 0) {
                throw new Exception();
            }

            ParametersChanger.PARAMETERS_CHANGER.changeM2(value);
            m2TextField.setPromptText(m2TextField.getText());
        } catch (Exception ignored) {
            f4 = false;
        }

        try {
            double value = Double.parseDouble(m3TextField.getText());

            if (value <= 0) {
                throw new Exception();
            }

            ParametersChanger.PARAMETERS_CHANGER.changeM3(value);
            m3TextField.setPromptText(m3TextField.getText());
        } catch (Exception e) {
            f5 = false;
        }
        try {
            double value = Double.parseDouble(vTextField.getText());

            if (value <= 0) {
                throw new Exception();
            }

            ParametersChanger.PARAMETERS_CHANGER.changeV1(value);
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
        output += f6 ? "v " : "";
        output += f3 ? "m1 " : "";
        output += f4 ? "m2 " : "";
        output += f5 ? "m3 " : "";

        changeMessage.setText(output + "успешно изменен(ы)!");
    }

}
