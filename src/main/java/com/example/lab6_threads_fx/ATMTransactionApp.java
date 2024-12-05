package com.example.lab6_threads_fx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.scene.layout.Panel;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ATMTransactionApp extends Application {
    private Account account;
    private ExecutorService executorService;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ATM Transaction Simulator");

        // Create main panel with BootstrapFX
        Panel mainPanel = new Panel("ATM Transaction Simulator");
        mainPanel.getStyleClass().add("panel-primary");

        // Create UI Components
        Label balanceLabel = new Label("Current Balance: $1000.00");
        balanceLabel.getStyleClass().addAll("h3", "text-primary");

        // Initialize account with balance label
        account = new Account(1000, balanceLabel);

        // Transaction input
        TextField amountField = new TextField();
        amountField.setPromptText("Enter Amount");
        amountField.getStyleClass().add("form-control");

        // Buttons
        Button depositButton = new Button("Deposit");
        depositButton.getStyleClass().addAll("btn", "btn-success");

        Button withdrawButton = new Button("Withdraw");
        withdrawButton.getStyleClass().addAll("btn", "btn-danger");

        // Log Area
        TextArea logArea = new TextArea();
        logArea.setId("logArea");
        logArea.setEditable(false);
        logArea.setPrefHeight(200);
        logArea.getStyleClass().add("form-control");

        // Layout
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setPadding(new Insets(20));
        inputGrid.add(new Label("Amount:"), 0, 0);
        inputGrid.add(amountField, 1, 0);
        inputGrid.add(depositButton, 0, 1);
        inputGrid.add(withdrawButton, 1, 1);

        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.getChildren().addAll(
                balanceLabel,
                inputGrid,
                new Label("Transaction Log:"),
                logArea
        );

        // Add content to main panel
        mainPanel.setBody(content);

        // Button Actions
        depositButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                executorService.execute(new Transaction(account, "deposit", amount));
                amountField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Please enter a valid number");
            }
        });

        withdrawButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                executorService.execute(new Transaction(account, "withdraw", amount));
                amountField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Please enter a valid number");
            }
        });

        // Create scene and apply BootstrapFX
        Scene scene = new Scene(mainPanel, 400, 500);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

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
        alert.getDialogPane().getStyleClass().add("alert-danger");
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