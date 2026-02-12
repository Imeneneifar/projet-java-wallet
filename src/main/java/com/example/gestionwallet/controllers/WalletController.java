package com.example.gestionwallet.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import com.example.gestionwallet.utils.database;

public class WalletController {

    @FXML private VBox outcomeBox;
    @FXML private VBox incomeBox;
    @FXML private Label balanceLabel;

    private double balance = 0;
    private VBox outcomeList;
    private VBox incomeList;

    @FXML
    public void initialize() {

        VBox outcomeCard = createCard("Outcome", false);
        VBox incomeCard = createCard("Income", true);

        outcomeBox.getChildren().add(outcomeCard);
        incomeBox.getChildren().add(incomeCard);

        loadTransactions();
    }

    private VBox createCard(String title, boolean isIncome) {

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size:18; -fx-font-weight:bold;");

        Button addBtn = new Button("+ Add");
        addBtn.setOnAction(e -> openAddTransaction(isIncome));

        addBtn.setStyle(isIncome
                ? "-fx-background-color:#2ecc71; -fx-text-fill:white;"
                : "-fx-background-color:#e74c3c; -fx-text-fill:white;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(10, titleLabel, spacer, addBtn);

        VBox list = new VBox(10);
        list.setPadding(new Insets(10));

        if (isIncome) incomeList = list;
        else outcomeList = list;

        ScrollPane scrollPane = new ScrollPane(list);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(350);

        VBox card = new VBox(15, header, scrollPane);
        card.setPadding(new Insets(20));
        card.setPrefWidth(420);
        card.setStyle("""
                -fx-background-color:white;
                -fx-background-radius:16;
                -fx-effect:dropshadow(gaussian, rgba(0,0,0,0.15), 15, 0, 0, 5);
                """);

        return card;
    }

    private void openAddTransaction(boolean isIncome) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/gestionwallet/add-transaction.fxml")
            );

            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());

            AddTransactionController controller = loader.getController();
            controller.setType(isIncome ? "INCOME" : "OUTCOME");
            controller.setParentController(this);

            stage.setScene(scene);
            stage.setTitle("Add Transaction");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadTransactions() {

        incomeList.getChildren().clear();
        outcomeList.getChildren().clear();
        balance = 0;

        try {

            Connection cnx = database.getInstance().getConnection();

            String sql = """
                    SELECT t.nom_transaction,
                           t.type,
                           t.montant,
                           c.nom AS category_name
                    FROM transaction t
                    JOIN category c ON t.category_id = c.id_category
                    """;

            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                String name = rs.getString("nom_transaction");
                String type = rs.getString("type");
                double amount = rs.getDouble("montant");
                String category = rs.getString("category_name");

                addTransaction(name, category, amount, type);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        updateBalanceLabel();
    }

    private void addTransaction(String name, String category, double amount, String type) {

        HBox item = new HBox(10);
        item.setPadding(new Insets(10));
        item.setStyle("""
                -fx-background-color:#f8f9fb;
                -fx-background-radius:12;
                """);

        // 🔥 اسم الترانزاكسيون + الكاتيجوري
        Label nameLabel = new Label(name + " (" + category + ")");
        nameLabel.setStyle("-fx-font-weight:bold;");

        Label amountLabel = new Label(amount + " DT");

        amountLabel.setStyle(amount >= 0
                ? "-fx-text-fill:#2ecc71;"
                : "-fx-text-fill:#e74c3c;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        item.getChildren().addAll(nameLabel, spacer, amountLabel);

        if (type.equals("INCOME")) {
            incomeList.getChildren().add(item);
        } else {
            outcomeList.getChildren().add(item);
        }

        balance += amount;
    }

    private void updateBalanceLabel() {

        balanceLabel.setText("Balance: " + balance + " DT");

        balanceLabel.setStyle(balance < 0
                ? "-fx-font-size:26; -fx-font-weight:bold; -fx-text-fill:red;"
                : "-fx-font-size:26; -fx-font-weight:bold; -fx-text-fill:#1f7f4c;");
    }
}
