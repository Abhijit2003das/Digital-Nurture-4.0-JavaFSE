package com.example;

public class BankAccount {

    private int balance;

    public BankAccount(int initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Cannot deposit negative amount");
        balance += amount;
    }

    public void withdraw(int amount) {
        if (amount > balance) throw new IllegalArgumentException("Insufficient funds");
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }

    public void resetAccount() {
        balance = 0;
    }
}
