package com.cognizant.account;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController // This annotation combines @Controller and @ResponseBody
public class AccountController {

    @GetMapping("/accounts/{number}") // Maps HTTP GET requests to /accounts/{number}
    public Account getAccountDetails(@PathVariable String number) {
        // This is a dummy response as per requirements, no backend connectivity
        System.out.println("Received request for account number: " + number);
        return new Account(number, "savings", 234343.00);
    }
}

// A simple POJO (Plain Old Java Object) to represent account details
class Account {
    private String number;
    private String type;
    private double balance;

    public Account(String number, String type, double balance) {
        this.number = number;
        this.type = type;
        this.balance = balance;
    }

    // Getters (and setters if needed, but not strictly for this example)
    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }

    // Optional: toString for better logging/debugging
    @Override
    public String toString() {
        return "Account{" +
               "number='" + number + '\'' +
               ", type='" + type + '\'' +
               ", balance=" + balance +
               '}';
    }
}