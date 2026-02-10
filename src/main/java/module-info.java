module com.example.gestionwallet {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    opens com.example.gestionwallet to javafx.fxml;
    exports com.example.gestionwallet;
    exports com.example.gestionwallet.services;
    opens com.example.gestionwallet.services to javafx.fxml;
    exports com.example.gestionwallet.test;
    opens com.example.gestionwallet.test to javafx.fxml;
}