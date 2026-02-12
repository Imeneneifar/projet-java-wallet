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
        addBtn.setStyle(isIncome
                ? "-fx-background-color:#2ecc71; -fx-text-fill:white;"
                : "-fx-background-color:#e74c3c; -fx-text-fill:white;");

        addBtn.setOnAction(e -> openAddTransaction(isIncome));

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
            addTransaction(t);
        }

        updateBalanceLabel();
    }

    private void addTransaction(transaction t) {

        HBox item = new HBox(10);
        item.setPadding(new Insets(10));

        Label nameLabel = new Label(t.getNom_transaction());
        nameLabel.setStyle("-fx-font-weight:bold;");

        Label amountLabel = new Label(t.getMontant() + " DT");
        amountLabel.setStyle(t.getMontant() >= 0
                ? "-fx-text-fill:#2ecc71;"
                : "-fx-text-fill:#e74c3c;");

        Button editBtn = new Button("Modifier");
        editBtn.setStyle("-fx-background-color:#3498db; -fx-text-fill:white;");

        Button deleteBtn = new Button("Supprimer");
        deleteBtn.setStyle("-fx-background-color:#e74c3c; -fx-text-fill:white;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        item.getChildren().addAll(nameLabel, spacer, amountLabel, editBtn, deleteBtn);

        // ================= SUPPRIMER =================
        deleteBtn.setOnAction(e -> {

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText("Supprimer transaction ?");
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    st.supprimer(t.getId_transaction());
                    loadTransactions();
                }
            });
        });

        // ================= MODIFIER (NO TYPE) =================
        editBtn.setOnAction(e -> {

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Modifier Transaction");

            TextField nameField = new TextField(t.getNom_transaction());
            TextField amountField = new TextField(String.valueOf(t.getMontant()));

            VBox content = new VBox(10,
                    new Label("Nom:"), nameField,
                    new Label("Montant:"), amountField
            );
            content.setPadding(new Insets(10));

            dialog.getDialogPane().setContent(content);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            dialog.showAndWait().ifPresent(response -> {

                if (response == ButtonType.OK) {

                    try {

                        t.setNom_transaction(nameField.getText());
                        t.setMontant(Double.parseDouble(amountField.getText()));

                        st.modifier(t);  // update DB
                        loadTransactions();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        });

        if (t.getType().equals("INCOME"))
            incomeList.getChildren().add(item);
        else
            outcomeList.getChildren().add(item);

        balance += t.getMontant();
    }

    private void updateBalanceLabel() {

        balanceLabel.setText("Balance: " + balance + " DT");

        balanceLabel.setStyle(balance < 0
                ? "-fx-font-size:26; -fx-font-weight:bold; -fx-text-fill:red;"
                : "-fx-font-size:26; -fx-font-weight:bold; -fx-text-fill:#1f7f4c;");
    }
}
