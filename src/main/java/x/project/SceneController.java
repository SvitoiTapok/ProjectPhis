package x.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
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
    private Label changeMessage;


    @FXML
    private Pane pane;


    private double mu=0.04, k, m1, m2, m3;
    @FXML
    void myChange(ActionEvent event) {
        boolean f1 = false,f2=false,f3=false,f4=false,f5=false;
        try {
            double mu = Double.parseDouble(muTextField.getText());
            f1=(mu!=this.mu);
            System.out.println(mu +" " + this.mu + f1);
            this.mu = mu;

            ParametersChanger.PARAMETERS_CHANGER.changeMu(mu);
            muTextField.setPromptText(muTextField.getText());

        }catch (Exception ignored){}

        try {
            double k = Double.parseDouble(kTextField.getText());
            f2=(k!=this.k);
            this.k = k;
            ParametersChanger.PARAMETERS_CHANGER.changek(k);
            kTextField.setPromptText(kTextField.getText());
        }catch (Exception ignored){}

        try {
            double m1 = Double.parseDouble(m1TextField.getText());
            f3=(m1!=this.m1);
            this.m1 = m1;
            ParametersChanger.PARAMETERS_CHANGER.changeM1(m1);
            m1TextField.setPromptText(m1TextField.getText());
        }catch (Exception ignored){}

        try {
            double m2 = Double.parseDouble(m2TextField.getText());
            f4=(m2!=this.m2);
            this.m2 = m2;
            ParametersChanger.PARAMETERS_CHANGER.changeM2(m2);
            m2TextField.setPromptText(m2TextField.getText());
        }catch (Exception ignored){}

        try {
            double m3 = Double.parseDouble(m3TextField.getText());
            f5=(m3!=this.m3);
            this.m3 = m3;
            ParametersChanger.PARAMETERS_CHANGER.changeM3(m3);
            m3TextField.setPromptText(m3TextField.getText());
        }catch (Exception ignored){}
        if(!(f1||f2||f3||f4||f5)) {
            changeMessage.setText("Не удалось изменить величины");
            return;
        }
        String output = "";
        if(f1)output+="mu ";
        if(f2)output+="k ";
        if(f3)output+="m1 ";
        if(f4)output+="m2 ";
        if(f5)output+="m3 ";
        changeMessage.setText(output += "успешно изменен(ы)!");
    }

}
