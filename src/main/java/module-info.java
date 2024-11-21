module x.project {
    requires javafx.controls;
    requires javafx.fxml;


    opens x.project to javafx.fxml;
    exports x.project;
}