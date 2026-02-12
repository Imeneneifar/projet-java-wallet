package com.example.gestionwallet.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.example.gestionwallet.utils.database;

public class AddTransactionController {

    @FXML private TextField nameField;
    @FXML private TextField amountField;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> categoryBox;
    @FXML private ComboBox<String> typeBox;

    private WalletController parentController;
    private String currentType;

    @FXML
    public void initialize() {
        typeBox.getItems().addAll("OUTCOME", "INCOME");
    }

    public void setParentController(WalletController controller) {
        this.parentController = controller;
    }

    public void setType(String type) {
        this.currentType = type;
        typeBox.setValue(type);
        typeBox.setDisable(true);
        loadCategoriesByType(type);
    }

    private void loadCategoriesByType(String type) {

        try {
            Connection cnx = database.getInstance().getConnection();

            String sql = "SELECT nom FROM category WHERE type = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, type);

            ResultSet rs = ps.executeQuery();

            categoryBox.getItems().clear();

            while (rs.next()) {
                categoryBox.getItems().add(rs.getString("nom"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getCategoryIdByName(String name) throws Exception {

        Connection cnx = database.getInstance().getConnection();

        String sql = "SELECT id_category FROM category WHERE nom = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, name);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("id_category");
        }

        throw new Exception("Category not found");
    }

    @FXML
    private void saveTransaction() {

        try {

            String name = nameField.getText();
            double amount = Double.parseDouble(amountField.getText());
            String type = typeBox.getValue();
            String categoryName = categoryBox.getValue();

            if (type.equals("OUTCOME")) {
                amount = -amount;
            }

            int categoryId = getCategoryIdByName(categoryName);

            Connection cnx = database.getInstance().getConnection();

            String sql = """
                    INSERT INTO transaction
                    (nom_transaction, type, montant, date_transaction, source, user_id, category_id)
                    VALUES (?, ?, ?, ?, 'MANUAL', 1, ?)
                    """;

            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, type);
            ps.setDouble(3, amount);
            ps.setDate(4, java.sql.Date.valueOf(datePicker.getValue()));
            ps.setInt(5, categoryId);

            ps.executeUpdate();

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

            // 🔥 المهم
            controller.setCategoryType(currentType);

            stage.showAndWait();

            // 🔥 refresh بعد الإضافة
            loadCategoriesByType(currentType);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
