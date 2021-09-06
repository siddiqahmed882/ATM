package atm;

import java.util.ArrayList;

public class Account {
  private String name;
  private String uuid;
  /* The user objects that hold this account */
  private User holder;
  /* The list of transaction for this account */
  private ArrayList<Transaction> transactions;

  public Account(String name, User holder, Bank theBank) {
    this.name = name;
    this.holder = holder;
    // get new uuid
    this.uuid = theBank.getNewAccountUUID();

    // init transactions
    this.transactions = new ArrayList<Transaction>();

    // add account holder and bank lists
    // holder.addAccount(this);
    // theBank.addAccount(this);
  }

  public String getUUID() {
    return this.uuid;
  }

  /**
   * Get summary line for the acount
   * 
   * @return the string summary
   */
  public String getSummaryLine() {
    // get the account's balance
    double balance = this.getBalance();

    // format the summary line, depending ton whether balance is negative
    if (balance >= 0) {
      return String.format("%s : $%s : %s", this.uuid, balance, this.name);
    } else {
      return String.format("%s : $(%s) : %s", this.uuid, balance, this.name);
    }
  }

  /* Get balance */
  public double getBalance() {
    double balance = 0;
    for (Transaction t : transactions) {
      balance += t.getAmount();
    }
    return balance;
  }

  /**
   * Print the transaction history of the account
   */
  public void printTransHistory() {
    System.out.printf("\nTransaction histrory for account %s\n", this.uuid);
    for (int t = this.transactions.size() - 1; t >= 0; t--) {
      System.out.printf(this.transactions.get(t).getSummaryLine());
      System.out.println();
    }
    System.out.println();
  }

  /**
   * Add a new transaction in this account
   * 
   * @param amount the amount transacted
   * @param memo   the transaction memo
   */
  public void addTransaction(double amount, String memo) {
    // create new transaction object and add to the list
    Transaction newTrans = new Transaction(amount, memo, this);
    this.transactions.add(newTrans);
  }
}
