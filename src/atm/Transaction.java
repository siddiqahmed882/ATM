package atm;

import java.util.Date;

public class Transaction {
  private double amount;
  private Date timeStamp;
  /* A memo for this transaction */
  private String memo;
  /* The account in which the transaction was performed */
  private Account inAccount;

  /**
   * 
   * @param amount
   * @param inAccount
   */
  public Transaction(double amount, Account inAccount) {
    this.amount = amount;
    this.inAccount = inAccount;
    this.timeStamp = new Date();
    this.memo = "";
  }

  /**
   * 
   * @param amount
   * @param memo
   * @param inAccount
   */
  public Transaction(double amount, String memo, Account inAccount) {
    // call the two args constructor;
    this(amount, inAccount);
    // set memo
    this.memo = memo;
  }
}
