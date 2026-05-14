module com.example.towerdefence {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.towerdefence to javafx.fxml;
    exports com.example.towerdefence;
}