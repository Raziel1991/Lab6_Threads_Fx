# ATM Transaction Simulator

## Overview
This is a JavaFX application demonstrating synchronized ATM transactions using concurrent programming techniques.

## Features
- Thread-safe account operations
- Deposit and withdraw functionality
- Real-time balance updates
- Transaction logging
- Error handling

## Requirements
- Java 11+
- JavaFX SDK

## Running the Application
1. Compile all Java files
2. Ensure JavaFX libraries are in the classpath
3. Run the `ATMTransactionApp` main class

## Key Components
- `Account.java`: Manages account balance with synchronized methods
- `Transaction.java`: Represents individual deposit/withdraw transactions
- `ATMTransactionApp.java`: JavaFX application with user interface

## Concurrency Highlights
- Uses `ExecutorService` for thread management
- `synchronized` methods prevent race conditions
- `Platform.runLater()` for safe UI updates
