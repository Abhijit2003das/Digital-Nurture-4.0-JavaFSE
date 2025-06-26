package com.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BankAccountTest {

    private BankAccount account;

    // Setup before each test
    @Before
    public void setUp() {
        account = new BankAccount(100);  // Arrange
        System.out.println("Setup complete");
    }

    // Teardown after each test
    @After
    public void tearDown() {
        account = null;
        System.out.println("Teardown complete");
    }

    @Test
    public void testDeposit() {
        // Arrange (done in @Before)
        // Act
        account.deposit(50);
        // Assert
        assertEquals(150, account.getBalance());
    }

    @Test
    public void testWithdraw() {
        // Arrange
        // Act
        account.withdraw(30);
        // Assert
        assertEquals(70, account.getBalance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawTooMuch() {
        // Act
        account.withdraw(200);
        // Assert is handled by expected exception
    }

    @Test
    public void testResetAccount() {
        // Act
        account.resetAccount();
        // Assert
        assertEquals(0, account.getBalance());
    }
}
