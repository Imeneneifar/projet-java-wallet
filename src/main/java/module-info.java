module com.example.gestionwallet {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    opens com.example.gestionwallet to javafx.fxml;
    exports com.example.gestionwallet;
}