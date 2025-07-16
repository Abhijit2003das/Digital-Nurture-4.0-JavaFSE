package com.cognizant.loan;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanController {

    @GetMapping("/loans/{number}")
    public Loan getLoanDetails(@PathVariable String number) {
        // This is a dummy response as per requirements, no backend connectivity.
        System.out.println("Received request for loan number: " + number);
        return new Loan(number, "car", 400000.0, 3258.0, 18);
    }
}

// A simple POJO to represent loan details
class Loan {
    private String number;
    private String type;
    private double loanAmount;
    private double emi;
    private int tenure; // in months

    public Loan(String number, String type, double loanAmount, double emi, int tenure) {
        this.number = number;
        this.type = type;
        this.loanAmount = loanAmount;
        this.emi = emi;
        this.tenure = tenure;
    }

    // Getters
    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public double getEmi() {
        return emi;
    }

    public int getTenure() {
        return tenure;
    }

    // Optional: toString
    @Override
    public String toString() {
        return "Loan{" +
               "number='" + number + '\'' +
               ", type='" + type + '\'' +
               ", loanAmount=" + loanAmount +
               ", emi=" + emi +
               ", tenure=" + tenure +
               '}';
    }
}