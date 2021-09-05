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
}
