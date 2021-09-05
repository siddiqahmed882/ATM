package atm;

import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
  /* First Name of the user */
  private String firstName;
  /* Last Name of the user */
  private String lastName;
  /* The ID number of the user */
  private String uuid;
  /* The MD5 hash of user's pin */
  private byte pinHash[];
  /* The list of accounts of this user */
  private ArrayList<Account> accounts;

  public User(String firstName, String lastName, String pin, Bank theBank) {
    // Store user's name
    this.firstName = firstName;
    this.lastName = lastName;
    // store the pin's MD5 hash, rather than the original value
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      this.pinHash = md.digest(pin.getBytes());
    } catch (NoSuchAlgorithmException e) {
      // TODO Auto-generated catch block
      System.err.println("Error, caught NoSuchAlgorithmException");
      e.printStackTrace();
      System.exit(1);
    }
    // get a new, unique universal identifier for the user
    this.uuid = theBank.getNewUserUUID();
    // create empty list of accounts
    this.accounts = new ArrayList<Account>();
    // print log message
    System.out.printf("New User %s %s with ID %s created. \n", firstName, lastName, this.uuid);
  }

  public void addAccount(Account account) {
    this.accounts.add(account);
  }

  public String getUUID() {
    return this.uuid;
  }

  /**
   * Check whether a given pin matches the true user pin
   * 
   * @param pin the pin to check
   * @return whether the pin is valid or not
   */
  public boolean validatePin(String pin) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);
    } catch (NoSuchAlgorithmException e) {
      System.err.println("Error, caught NoSuchAlgorithmException");
      e.printStackTrace();
      System.exit(1);
    }
    return false;
  }
}
