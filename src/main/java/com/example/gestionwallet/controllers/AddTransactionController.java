package com.example.gestionwallet.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.example.gestionwallet.models.transaction;
import com.example.gestionwallet.models.categorie;
import com.example.gestionwallet.services.servicetransaction;
import com.example.gestionwallet.services.servicecategorie;

import java.util.List;

public class AddTransactionController {

    @FXML private TextField nameField;
    @FXML private TextField amountField;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> categoryBox;
    @FXML private Label typeLabel;

    private WalletController parentController;
    private String currentType;

    private servicetransaction st = new servicetransaction();
    private servicecategorie sc = new servicecategorie();

    @FXML
    public void initialize() {
        // nothing special here now
    }

    public void setParentController(WalletController controller) {
        this.parentController = controller;
    }

    public void setType(String type) {

        this.currentType = type;

        typeLabel.setText(type);

        if(type.equals("INCOME")){
            typeLabel.setStyle("-fx-text-fill:#2ecc71; -fx-font-weight:bold;");
        } else {
            typeLabel.setStyle("-fx-text-fill:#e74c3c; -fx-font-weight:bold;");
        }

        loadCategoriesByType(type);
    }

    private void loadCategoriesByType(String type) {

        categoryBox.getItems().clear();

        List<categorie> list = sc.afficher();

        for (categorie c : list) {
            if (c.getType().equals(type)) {
                categoryBox.getItems().add(c.getNom());
            }
        }
    }

    @FXML
    private void saveTransaction() {

        try {

            String name = nameField.getText();
            double amount = Double.parseDouble(amountField.getText());
            String type = currentType;
            String categoryName = categoryBox.getValue();

            if (type.equals("OUTCOME")) {
                amount = -amount;
            }

            int categoryId = sc.getIdByName(categoryName);

            transaction t = new transaction(
                    name,
                    type,
                    amount,
                    java.sql.Date.valueOf(datePicker.getValue()),
                    "MANUAL",
                    1,
                    categoryId
            );

            st.ajouter(t);

            parentController.loadTransactions();

            ((Stage) nameField.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openAddCategory() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/gestionwallet/add-category.fxml")
            );

            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);

            AddCategoryController controller = loader.getController();
            controller.setCategoryType(currentType);

            stage.showAndWait();

            loadCategoriesByType(currentType);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
