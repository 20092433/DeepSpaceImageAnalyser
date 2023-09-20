module com.example.worksheetone {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.worksheetone to javafx.fxml;
    exports com.example.worksheetone;
}