package com.example.gestionwallet.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.gestionwallet.models.categorie;
import com.example.gestionwallet.services.servicecategorie;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.example.gestionwallet.utils.database;

public class AddCategoryController {

    @FXML private TextField nameField;
    @FXML private ComboBox<String> priorityBox;
    private servicecategorie sc = new servicecategorie();

    private String categoryType; // INCOME / OUTCOME

    @FXML
    public void initialize() {
        priorityBox.getItems().addAll("HAUTE", "MOYENNE", "BASSE");
    }

    public void setCategoryType(String type) {
        this.categoryType = type;
    }

    @FXML
    private void saveCategory() {

        String name = nameField.getText();
        String priority = priorityBox.getValue();

        if (name == null || name.isEmpty() || priority == null) return;

        categorie c = new categorie(name, priority, categoryType);
        sc.ajouter(c);

        ((Stage) nameField.getScene().getWindow()).close();
    }


}
