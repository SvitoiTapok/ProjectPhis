module x.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires jdk.security.auth;

    opens x.project to javafx.fxml;
    exports x.project;
}