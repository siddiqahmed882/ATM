package atm;

import java.util.Scanner;

public class ATM {

    /* Main Menu Prompt */
    public static User mainMenuPrompt(Bank theBank, Scanner sc) {
        // inits
        String userID;
        String pin;
        User authUser;

        // prompt the user for user ID/pin combo untill a correct one is reached
        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID: ");
            userID = sc.nextLine();
            System.out.print("Enter pin: ");
            pin = sc.nextLine();

            // try to get the user object corresponding to the id and pin combo
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incoorect User ID/pin combination. Please try again");

            }
        } while (authUser == null); // continue looping until successful login
        return authUser;
    }

    /* User Main Menu */
    public static void printUserMenu(User theUser, Scanner sc) {
        // print a summary of the user's account
        theUser.printAccountsSummary();
        // init
        int choice;
        // user menu
        do {
            System.out.printf("Welcome %s, what would you like to do?\n", theUser.getFirstName());
            System.out.println(" 1) Show transaction history for the account");
            System.out.println(" 2) Withdraw");
            System.out.println(" 3) Deposit");
            System.out.println(" 4) Transfer");
            System.out.println(" 5) Quit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Please choose between 1 - 5 ");
            }
        } while (choice < 1 || choice > 5);

        // process the choice
        switch (choice) {
            case 1:
                ATM.showTransHistory(theUser, sc);
                break;
            case 2:
                ATM.withDrawFunds(theUser, sc);
                break;
            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.transferFunds(theUser, sc);
                break;
        }
        // redisplay this menu unless user wants to quit
        if (choice != 5) {
            ATM.printUserMenu(theUser, sc);
        }
    }

    /**
     * Show Transaction History
     * 
     * @param theUser the logged in user
     * @param sc      the scanner object
     */
    public static void showTransHistory(User theUser, Scanner sc) {
        int theAcct;

        // get account whose transaction history to look at
        do {
            System.out.printf("Enter the account number (1-%d) of the account whose tansactions you want to see",
                    theUser.numAccounts());
            theAcct = sc.nextInt() - 1;
            if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
                System.out.println("Invalid Account. Please try again");
            }
        } while (theAcct < 0 || theAcct >= theUser.numAccounts());
        // print the transaction history
        theUser.printAccTransHistory(theAcct);
    }

    /**
     * Process transferring funds from one account to another
     * 
     * @param theUser the logged in user
     * @param sc      Scanner object for user input
     */
    public static void transferFunds(User theUser, Scanner sc) {
        // init
        int fromAcc;
        int toAcc;
        double amount;
        double accBalance;
        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account to trasfer from: ", theUser.numAccounts());
            fromAcc = sc.nextInt() - 1;
            if (fromAcc < 0 || fromAcc >= theUser.numAccounts()) {
                System.out.println("Invalid Account. Please try again");
            }
        } while (fromAcc < 0 || fromAcc >= theUser.numAccounts());

        accBalance = theUser.getAcctBalance(fromAcc);

        // get the account to transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the account to trasfer to: ", theUser.numAccounts());
            toAcc = sc.nextInt() - 1;
            if (toAcc < 0 || toAcc >= theUser.numAccounts()) {
                System.out.println("Invalid Account. Please try again");
            }
        } while (toAcc < 0 || toAcc >= theUser.numAccounts());

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max %.02f) : $", accBalance);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero");
            } else if (amount > accBalance) {
                System.out.printf("Amount must not be greater than $%.02f", accBalance);
            }
        } while (amount < 0 || amount > accBalance);

        // finally do the transfer
        theUser.addAccTransaction(fromAcc, -1 * amount,
                String.format("Transfer to account %s", theUser.getAcctUUID(toAcc)));
        theUser.addAccTransaction(toAcc, amount,
                String.format("Transfer from account %s", theUser.getAcctUUID(fromAcc)));
    }

    /**
     * Process a fund withdraw from an account
     * 
     * @param theUser the logged in user object
     * @param sc      the scanner object for user input
     */
    public static void withDrawFunds(User theUser, Scanner sc) {
        // init
        int fromAcc;
        double amount;
        double accBalance;
        String memo;
        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account to withdraw from: ", theUser.numAccounts());
            fromAcc = sc.nextInt() - 1;
            if (fromAcc < 0 || fromAcc >= theUser.numAccounts()) {
                System.out.println("Invalid Account. Please try again");
            }
        } while (fromAcc < 0 || fromAcc >= theUser.numAccounts());

        accBalance = theUser.getAcctBalance(fromAcc);

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max %.02f) : $", accBalance);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero");
            } else if (amount > accBalance) {
                System.out.printf("Amount must not be greater than\n" + "balance of $%.02f \n", accBalance);
            }
        } while (amount < 0 || amount > accBalance);

        // gobble up rest of previous input
        sc.nextLine();

        // get the memo
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();

        // do the with drawal
        theUser.addAccTransaction(fromAcc, -1 * amount, memo);
    }

    /**
     * Process a fund deposit to an account
     * 
     * @param theUser the logged in user object
     * @param sc      the scanner object for user input
     */
    public static void depositFunds(User theUser, Scanner sc) {
        // init
        int toAcc;
        double amount;
        double accBalance;
        String memo;
        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account to deposit in: ", theUser.numAccounts());
            toAcc = sc.nextInt() - 1;
            if (toAcc < 0 || toAcc >= theUser.numAccounts()) {
                System.out.println("Invalid Account. Please try again");
            }
        } while (toAcc < 0 || toAcc >= theUser.numAccounts());

        accBalance = theUser.getAcctBalance(toAcc);

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max %.02f) : $", accBalance);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero");
            }
        } while (amount < 0);

        // gobble up rest of previous input
        sc.nextLine();

        // get the memo
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();

        // do the with drawal
        theUser.addAccTransaction(toAcc, amount, memo);
    }

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

        User curUser;
        while (true) {
            // Stay in he login promp until successfull
            curUser = ATM.mainMenuPrompt(theBank, sc);

            // stay in main menu until user quits
            ATM.printUserMenu(curUser, sc);
        }
    }
}
