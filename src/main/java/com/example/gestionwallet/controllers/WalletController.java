package com.example.gestionwallet.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class WalletController {

    @FXML
    private VBox outcomeBox;

    @FXML
    private VBox incomeBox;

    @FXML
    public void initialize() {

        // ===== Outcome card =====
        VBox outcomeCard = createCard("Outcome", false);
        VBox outcomeList = (VBox) ((ScrollPane) outcomeCard.getChildren().get(1)).getContent();

        outcomeList.getChildren().addAll(
                createItem("Transport", "20 DT • 24/04/2024", "-30 DT", false),
                createItem("Groceries", "15 DT • 23/04/2024", "-50 DT", false),
                createItem("Electricity", "80 DT • 22/04/2024", "-80 DT", false)
        );

        // ===== Income card =====
        VBox incomeCard = createCard("Income", true);
        VBox incomeList = (VBox) ((ScrollPane) incomeCard.getChildren().get(1)).getContent();

        incomeList.getChildren().addAll(
                createItem("Salary", "25/04/2024", "+1500 DT", true),
                createItem("Freelance", "23/04/2024", "+400 DT", true),
                createItem("Gift", "18/04/2024", "+100 DT", true)
        );

        outcomeBox.getChildren().add(outcomeCard);
        incomeBox.getChildren().add(incomeCard);
    }

    // ================= CARD =================
    private VBox createCard(String title, boolean isIncome) {

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size:18px; -fx-font-weight:bold;");

        Button addBtn = new Button("+ Add");
        addBtn.setStyle(isIncome
                ? "-fx-background-color:#2ecc71; -fx-text-fill:white;"
                : "-fx-background-color:#e74c3c; -fx-text-fill:white;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(10, titleLabel, spacer, addBtn);
        header.setAlignment(Pos.CENTER_LEFT);

        VBox list = new VBox(10);
        list.setPadding(new Insets(5));

        ScrollPane scrollPane = new ScrollPane(list);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(350);

        VBox card = new VBox(15, header, scrollPane);
        card.setPadding(new Insets(20));
        card.setPrefWidth(400);
        card.setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 16;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 15, 0, 0, 5);
                """);

        return card;
    }

    // ================= ITEM =================
    private HBox createItem(String title, String date, String amount, boolean isIncome) {

        Label icon = new Label(isIncome ? "💼" : "💸");
        icon.setStyle("-fx-font-size:20px;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-weight:bold;");

        Label dateLabel = new Label(date);
        dateLabel.setStyle("-fx-text-fill:#777;");

        VBox info = new VBox(4, titleLabel, dateLabel);

        Label amountLabel = new Label(amount);
        amountLabel.setStyle(isIncome
                ? "-fx-text-fill:#2ecc71; -fx-font-weight:bold;"
                : "-fx-text-fill:#e74c3c; -fx-font-weight:bold;");

        Button editBtn = new Button("✏");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox item = new HBox(10, icon, info, spacer, amountLabel, editBtn);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(10));
        item.setStyle("""
                -fx-background-color:#f8f9fb;
                -fx-background-radius:12;
                """);

        return item;
    }
}
