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

  /* Get Transaction Amount */
  public double getAmount() {
    return this.amount;
  }

  /**
   * Get a string summarazing the transaction
   * 
   * @return the summary string
   */
  public String getSummaryLine() {
    if (this.amount >= 0) {
      return String.format("%s : $% : %s", this.timeStamp.toString(), this.amount, this.memo);
    } else {
      return String.format("%s : $(%) : %s", this.timeStamp.toString(), this.amount, this.memo);
    }
  }
}
