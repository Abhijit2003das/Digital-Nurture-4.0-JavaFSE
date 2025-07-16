package com.cognizant.account.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @GetMapping("/account/status")
    public String getAccountStatus() {
        return "Account Service is Up and Running!";
    }
}