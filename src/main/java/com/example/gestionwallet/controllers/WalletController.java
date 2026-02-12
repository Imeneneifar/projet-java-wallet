package com.example.gestionwallet.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.example.gestionwallet.models.transaction;
import com.example.gestionwallet.services.servicetransaction;

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
    private servicetransaction st = new servicetransaction();

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

        for (transaction t : st.afficher()) {
            addTransaction(t.getNom_transaction(), t.getMontant(), t.getType());
        }

        updateBalanceLabel();
    }



    private void addTransaction(String name, double amount, String type) {

        HBox item = new HBox(10);
        item.setPadding(new Insets(10));

        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-weight:bold;");

        Label amountLabel = new Label(amount + " DT");
        amountLabel.setStyle(amount >= 0
                ? "-fx-text-fill:#2ecc71;"
                : "-fx-text-fill:#e74c3c;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        item.getChildren().addAll(nameLabel, spacer, amountLabel);

        if (type.equals("INCOME"))
            incomeList.getChildren().add(item);
        else
            outcomeList.getChildren().add(item);

        balance += amount;
    }

    private void updateBalanceLabel() {

        balanceLabel.setText("Balance: " + balance + " DT");

        balanceLabel.setStyle(balance < 0
                ? "-fx-font-size:26; -fx-font-weight:bold; -fx-text-fill:red;"
                : "-fx-font-size:26; -fx-font-weight:bold; -fx-text-fill:#1f7f4c;");
    }
}
