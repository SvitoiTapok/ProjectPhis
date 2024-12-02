module x.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    opens x.project to javafx.fxml;
    exports x.project;
}