package com.example.lab6_threads_fx;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class Account {
    private double balance;
    private Label balanceLabel;

    public Account(double initialBalance, Label balanceLabel) {
        this.balance = initialBalance;
        this.balanceLabel = balanceLabel;
    }

    // Synchronized deposit method
    public synchronized void deposit(double amount) {
        balance += amount;
        updateBalanceLabel();
        log("Deposited: $" + String.format("%.2f", amount));
    }

    // Synchronized withdraw method
    public synchronized boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            updateBalanceLabel();
            log("Withdrawn: $" + String.format("%.2f", amount));
            return true;
        } else {
            log("Insufficient funds. Withdrawal of $" + String.format("%.2f", amount) + " failed.");
            return false;
        }
    }

    // Update balance label on JavaFX Application Thread
    private void updateBalanceLabel() {
        Platform.runLater(() ->
                balanceLabel.setText(String.format("Current Balance: $%.2f", balance))
        );
    }

    // Log method to update text area
    private void log(String message) {
        Platform.runLater(() -> {
            TextArea logArea = (TextArea) balanceLabel.getScene().lookup("#logArea");
            if (logArea != null) {
                logArea.appendText(message + "\n");
            }
        });
    }

    public double getBalance() {
        return balance;
    }
}