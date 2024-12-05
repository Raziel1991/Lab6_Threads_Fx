package com.example.lab6_threads_fx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ATMTransactionApp extends Application {
    private Account account;
    private ExecutorService executorService;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ATM Transaction Simulator");

        // Create UI Components
        Label balanceLabel = new Label("Current Balance: $1000.00");
        balanceLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Initialize account with balance label
        account = new Account(1000, balanceLabel);

        // Transaction input
        TextField amountField = new TextField();
        amountField.setPromptText("Enter Amount");

        // Buttons
        Button depositButton = new Button("Deposit");
        Button withdrawButton = new Button("Withdraw");

        // Log Area
        TextArea logArea = new TextArea();
        logArea.setId("logArea");
        logArea.setEditable(false);
        logArea.setPrefHeight(200);

        // Layout
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setPadding(new Insets(20));
        inputGrid.add(new Label("Amount:"), 0, 0);
        inputGrid.add(amountField, 1, 0);
        inputGrid.add(depositButton, 0, 1);
        inputGrid.add(withdrawButton, 1, 1);

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(
                balanceLabel,
                inputGrid,
                new Label("Transaction Log:"),
                logArea
        );

        // Button Actions
        depositButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                executorService.execute(new Transaction(account, "deposit", amount));
            } catch (NumberFormatException ex) {
                showAlert("Please enter a valid number");
            }
        });

        withdrawButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                executorService.execute(new Transaction(account, "withdraw", amount));
            } catch (NumberFormatException ex) {
                showAlert("Please enter a valid number");
            }
        });

        // Create scene
        Scene scene = new Scene(root, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initialize ExecutorService
        executorService = Executors.newFixedThreadPool(4);
    }

    // Helper method to show alerts
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Cleanup ExecutorService when application closes
    @Override
    public void stop() {
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}