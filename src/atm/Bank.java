package atm;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
  private String name;
  private ArrayList<User> users;
  private ArrayList<Account> accounts;

  /**
   * Create a new bank object with emoty list of users and accounts
   * 
   * @param name the name of the bank
   */
  public Bank(String name) {
    this.name = name;
    this.users = new ArrayList<User>();
    this.accounts = new ArrayList<Account>();
  }

  public String getNewUserUUID() {
    // init
    String uuid;
    Random rng = new Random();
    int len = 6;
    boolean nonUnique = false;

    // continue generating until we get a unique uuid
    do {
      uuid = "";
      for (int c = 0; c < len; c++) {
        uuid += ((Integer) rng.nextInt(10)).toString();
      }

      // check if unique;
      nonUnique = false;
      for (User u : this.users) {
        if (uuid.compareTo(u.getUUID()) == 0) {
          nonUnique = true;
          break;
        }
      }

    } while (nonUnique);
    return uuid;
  }

  public String getNewAccountUUID() {
    // init
    String uuid;
    Random rng = new Random();
    int len = 10;
    boolean nonUnique = false;

    // continue generating until we get a unique uuid
    do {
      uuid = "";
      for (int c = 0; c < len; c++) {
        uuid += ((Integer) rng.nextInt(10)).toString();
      }

      // check if unique;
      nonUnique = false;
      for (Account a : this.accounts) {
        if (uuid.compareTo(a.getUUID()) == 0) {
          nonUnique = true;
          break;
        }
      }
    } while (nonUnique);
    return uuid;
  }

  /**
   * Adds an account
   * 
   * @param account the account to add
   */
  public void addAccount(Account account) {
    this.accounts.add(account);
  }

  /**
   * create a new user of the bank
   * 
   * @param firstName
   * @param lastName
   * @param pin
   * @return the new user object
   */
  public User addUser(String firstName, String lastName, String pin) {

    // create a new user object
    User newUser = new User(firstName, lastName, pin, this);
    this.users.add(newUser);

    // create a savings account for the user and add to User and Bank accounts list
    Account newAccount = new Account("Savings", newUser, this);

    newUser.addAccount(newAccount);
    this.addAccount(newAccount);

    return newUser;
  }

  /**
   * Get the user associated with a particular userID and pin, if they are valid
   * 
   * @param userID the UUID of the user to login
   * @param pin    the pin of the user
   * @return the user object, if the login is successful, or null, if it is not
   */
  public User userLogin(String userID, String pin) {
    // search through the list of users
    for (User u : this.users) {
      // check user id is correct
      if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
        return u;
      }
    }
    // if haven't find the user or incorrect pin
    return null;
  }

  public Object getName() {
    return this.name;
  }
}
