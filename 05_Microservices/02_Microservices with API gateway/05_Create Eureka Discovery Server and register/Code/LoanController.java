package com.cognizant.loan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanController {

    @GetMapping("/loan/status")
    public String getLoanStatus() {
        return "Loan Service is Up and Running!";
    }
}