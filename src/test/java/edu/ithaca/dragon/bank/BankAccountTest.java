package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance());

        bankAccount.withdraw(30);
        assertEquals(70, bankAccount.getBalance());

        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.withdraw(-200)); // Border neg withdrawal
        assertThrows(InsufficientFundsException.class, ()-> bankAccount.withdraw(80)); //Border over withdrawal
        assertThrows(InsufficientFundsException.class, ()-> bankAccount.withdraw(1000)); //Equivalence over withdrawal
    }
    @Test
    void isAmountValidTest(){
        assertFalse(BankAccount.isAmountValid(-100));
        assertFalse(BankAccount.isAmountValid(-0.23));
        assertFalse(BankAccount.isAmountValid(.238923));
        assertFalse(BankAccount.isAmountValid(1.00000000));
        assertFalse(BankAccount.isAmountValid(0.310));
        assertTrue(BankAccount.isAmountValid(1));
        assertTrue(BankAccount.isAmountValid(1.2));
        assertTrue(BankAccount.isAmountValid(1.22));
        assertTrue(BankAccount.isAmountValid(10000.01));
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   //equivalence case
        assertTrue(BankAccount.isEmailValid(("a-c@b.com")));//equivalence case
        assertFalse(BankAccount.isEmailValid((".a@b.c")));  //border case
        assertFalse(BankAccount.isEmailValid((".a@b.com")));//equivalence case
        assertFalse(BankAccount.isEmailValid(("a#@b.com")));//border case
        assertTrue(BankAccount.isEmailValid(("a.d@b.com")));//border case
        assertTrue(BankAccount.isEmailValid(("a_s@b.com")));//border case
        assertFalse(BankAccount.isEmailValid(("a@.com")));  //equivalence case
        assertFalse(BankAccount.isEmailValid(("a@.comp")));  //equivalence case
        assertFalse( BankAccount.isEmailValid(""));         //equivalence case
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}