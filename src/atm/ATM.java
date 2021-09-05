package atm;

import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {

        // init Scanner
        Scanner sc = new Scanner(System.in);

        // init Bank
        Bank theBank = new Bank("Bank Of Drausin");

        // add a user to the bank, which also creates a saving account
        User aUser = theBank.addUser("John", "Doe", "1234");

        // add a checking account for our user
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);
    }

}
