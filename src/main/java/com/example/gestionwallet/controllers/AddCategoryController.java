package com.example.gestionwallet.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.example.gestionwallet.utils.database;

public class AddCategoryController {

    @FXML private TextField nameField;
    @FXML private ComboBox<String> priorityBox;

    private String categoryType;

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

        try {
            Connection cnx = database.getInstance().getConnection();

            String sql = "INSERT INTO category (nom, priorite, type) VALUES (?, ?, ?)";

            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, priority);
            ps.setString(3, categoryType);

            ps.executeUpdate();

            ((Stage) nameField.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
